package work.iruby.wshop.main.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.dao.ShoppingCartGoods;
import work.iruby.wshop.common.entity.Goods;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.common.entity.User;
import work.iruby.wshop.main.service.IGoodsService;
import work.iruby.wshop.main.service.IShopService;
import work.iruby.wshop.main.service.UserContext;
import work.iruby.wshop.common.rpc.RpcOrderService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IOrderServiceImpl {

    @DubboReference(version = "${wshop.service.version}")
    private RpcOrderService rpcOrderService;

    private final IGoodsService goodsService;
    private final IShopService shopService;

    @Autowired
    public IOrderServiceImpl(IGoodsService goodsService, IShopService shopService) {
        this.goodsService = goodsService;
        this.shopService = shopService;
    }

    public Object addOrder(OrderData order) {
        User currentUser = UserContext.getCurrentUser();
        if (order.getGoods() == null || order.getGoods().size() == 0) {
            return DataMessage.of(null);
        }
        Long totalPrice = deductStockAndCalTotalPrice(order);
        order.setTotalPrice(totalPrice);
        order.setAddress(currentUser.getAddress());
        DataMessage<OrderData> message = rpcOrderService.addOrder(order, currentUser.getId());
        dataFill(message.getData());
        return message;
    }

    public Object deleteOrderByOrderId(Long orderId) {
        Long userId = UserContext.getCurrentUser().getId();
        DataMessage<OrderData> message = rpcOrderService.deleteOrderByOrderId(orderId, userId);
        dataFill(message.getData());
        return message;
    }

    public Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus) {
        Long userId = UserContext.getCurrentUser().getId();
        DataMessage<OrderData> message = rpcOrderService.updateOrderByOrderId(orderId, orderExpressAndStatus, userId);
        dataFill(message.getData());
        return message;
    }

    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status) {
        Long userId = UserContext.getCurrentUser().getId();
        PageMessage<List<OrderData>> message = rpcOrderService.getCurrentUserPageOrders(pageNum, pageSize, status, userId);
        dataFill(message.getData());
        return message;
    }

    private Long deductStockAndCalTotalPrice(OrderData order) {
        List<ShoppingCartGoods> goodsIdAndNumberList = order.getGoods();
        long totalPrice = 0L;
        Map<Long, Integer> map = new HashMap<>();
        // 假如list包含相同id goods 进行合并
        goodsIdAndNumberList.forEach(goodsIdAndNumber -> {
            if (map.containsKey(goodsIdAndNumber.getId())) {
                map.put(goodsIdAndNumber.getId(), map.get(goodsIdAndNumber.getId()) + goodsIdAndNumber.getNumber());
            } else {
                map.put(goodsIdAndNumber.getId(), goodsIdAndNumber.getNumber());
            }

        });
        List<Goods> goodsList = goodsService.listByIds(map.keySet());
        Shop shop = new Shop();
        shop.setId(goodsList.get(0).getShopId());
        order.setShop(shop);
        for (Goods goods : goodsList) {
            Long goodsId = goods.getId();
            // 订单中需求的数量 大于 数据库中库存
            if (map.get(goodsId) > goods.getStock()) {
                throw new RuntimeException("id:" + goodsId + "的商品库存不足");
            } else {
                totalPrice = totalPrice + goods.getPrice() * map.get(goodsId);
                // 预计更新goods表后的库存
                goods.setStock(goods.getStock() - map.get(goodsId));
                goods.setUpdatedAt(LocalDateTime.now());
            }
        }
        // 更新库存信息(虽然这样不加锁必有问题
        goodsService.updateBatchById(goodsList);
        return totalPrice;
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
