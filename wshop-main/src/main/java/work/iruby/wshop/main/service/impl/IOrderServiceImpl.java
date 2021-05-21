package work.iruby.wshop.main.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.dao.ShoppingCartGoods;
import work.iruby.wshop.common.entity.Goods;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.common.exception.HttpException;
import work.iruby.wshop.common.rpc.RpcOrderService;
import work.iruby.wshop.main.service.IGoodsService;
import work.iruby.wshop.main.service.IOrderService;
import work.iruby.wshop.main.service.IShopService;
import work.iruby.wshop.main.service.UserContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IOrderServiceImpl implements IOrderService {

    @DubboReference(version = "${wshop.service.version}")
    private RpcOrderService rpcOrderService;

    private final IGoodsService goodsService;
    private final IShopService shopService;

    @Autowired
    public IOrderServiceImpl(IGoodsService goodsService, IShopService shopService) {
        this.goodsService = goodsService;
        this.shopService = shopService;
    }

    @Override
    public Object addOrder(OrderData order) {
        order.setAddress(UserContext.getCurrentUser().getAddress());
        DataMessage<OrderData> message = rpcOrderService.addOrder(order, UserContext.getCurrentUserId());
        dataFill(message.getData());
        return message;
    }

    @Override
    public Object deleteOrderByOrderId(Long orderId) {
        Long userId = UserContext.getCurrentUser().getId();
        DataMessage<OrderData> message = rpcOrderService.deleteOrderByOrderId(orderId, userId);
        dataFill(message.getData());
        return message;
    }

    @Override
    public Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus) {
        Long userId = UserContext.getCurrentUser().getId();
        DataMessage<OrderData> message = rpcOrderService.updateOrderByOrderId(orderId, orderExpressAndStatus, userId);
        dataFill(message.getData());
        return message;
    }

    @Override
    public Object getOrderByOrderId(Long orderId) {
        Long userId = UserContext.getCurrentUser().getId();
        DataMessage<OrderData> message = rpcOrderService.getOrderByOrderId(orderId, userId);
        dataFill(message.getData());
        return message;
    }

    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status) {
        Long userId = UserContext.getCurrentUser().getId();
        PageMessage<List<OrderData>> message = rpcOrderService.getCurrentUserPageOrders(pageNum, pageSize, status, userId);
        dataFill(message.getData());
        return message;
    }

    @Transactional
    public Long deductStockAndCalTotalPrice(OrderData order) {
        long totalPrice = 0L;
        Map<Long, Integer> map = order2goodsIdAndNumberMap(order);
        List<Goods> goodsList = goodsService.listByIds(map.keySet());
        Shop shop = new Shop();
        shop.setId(goodsList.get(0).getShopId());
        order.setShop(shop);
        for (Goods goods : goodsList) {
            Long goodsId = goods.getId();
            // 订单中需求的数量 大于 数据库中库存
            totalPrice = totalPrice + goods.getPrice() * map.get(goodsId);
            Boolean success = goodsService.deductStock(goods.getId(), map.get(goodsId));
            if (success == null || !success) {
                throw HttpException.gone("id为" + goods.getId() + "的商品库存不足");
            }
        }
        return totalPrice;
    }

    public Map<Long, Integer> order2goodsIdAndNumberMap(OrderData order) {
        List<ShoppingCartGoods> goodsIdAndNumberList = order.getGoods();
        Map<Long, Integer> map = new HashMap<>();
        // 假如list包含相同id goods 进行合并
        goodsIdAndNumberList.forEach(goodsIdAndNumber -> {
            if (map.containsKey(goodsIdAndNumber.getId())) {
                map.put(goodsIdAndNumber.getId(), map.get(goodsIdAndNumber.getId()) + goodsIdAndNumber.getNumber());
            } else {
                map.put(goodsIdAndNumber.getId(), goodsIdAndNumber.getNumber());
            }
        });
        return map;
    }

    private void dataFill(OrderData orderData) {
        if (orderData != null) {
            Shop shop = orderData.getShop();
            Shop dbShop = shopService.getById(shop.getId());
            BeanUtils.copyProperties(dbShop, shop);

            List<ShoppingCartGoods> goods = orderData.getGoods();
            List<Long> idList = goods.stream().map(Goods::getId).collect(Collectors.toList());
            List<Goods> dbGoodsList = goodsService.listByIds(idList);
            goodsFill(goods, dbGoodsList);
        }
    }

    private void goodsFill(List<ShoppingCartGoods> goods, List<Goods> dbGoodsList) {
        Map<Long, ShoppingCartGoods> goodsMap = goods.stream().collect(Collectors.toMap(Goods::getId, Goods -> Goods));
        dbGoodsList.forEach(db -> {
            if (goodsMap.containsKey(db.getId())) {
                BeanUtils.copyProperties(db, goodsMap.get(db.getId()));
            }
        });
    }

    private void dataFill(List<OrderData> orderDataList) {
        if (orderDataList != null) {
            for (OrderData orderData : orderDataList) {
                dataFill(orderData);
            }
        }
    }
}
