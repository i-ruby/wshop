package work.iruby.wshop.order.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.entity.OrderGoods;
import work.iruby.wshop.common.entity.OrderTable;
import work.iruby.wshop.common.enums.DataStatus;
import work.iruby.wshop.common.enums.OrderStatus;
import work.iruby.wshop.common.exception.HttpException;
import work.iruby.wshop.common.rpc.RpcOrderService;
import work.iruby.wshop.order.mapper.OrderDataMapper;
import work.iruby.wshop.order.service.IOrderGoodsService;
import work.iruby.wshop.order.service.IOrderTableService;

import java.time.LocalDateTime;
import java.util.List;

@DubboService(version = "${wshop.service.version}")
public class RpcOrderServiceImpl implements RpcOrderService {

    OrderDataMapper orderDataMapper;
    IOrderTableService orderTableService;
    IOrderGoodsService orderGoodsService;

    @Autowired
    public RpcOrderServiceImpl(OrderDataMapper orderDataMapper, IOrderTableService orderTableService, IOrderGoodsService orderGoodsService) {
        this.orderDataMapper = orderDataMapper;
        this.orderTableService = orderTableService;
        this.orderGoodsService = orderGoodsService;
    }

    @Override
    public DataMessage<OrderData> addOrder(OrderData order, Long userId) {
        OrderTable orderTable = new OrderTable();
        BeanUtils.copyProperties(order, orderTable);
        orderTable.setUserId(userId);
        orderTable.setShopId(order.getShop().getId());
        orderTable.setStatus(OrderStatus.PENDING.value());
        orderTable.setCreatedAt(LocalDateTime.now());
        orderTable.setUpdatedAt(LocalDateTime.now());
        orderTableService.save(orderTable);
        List<OrderGoods> orderGoodsList;
        String jsonString = JSON.toJSONString(order.getGoods());
        orderGoodsList = JSON.parseArray(jsonString, OrderGoods.class);
        orderGoodsList.forEach(orderGoods -> {
            orderGoods.setGoodsId(orderGoods.getId());
            orderGoods.setOrderId(orderTable.getId());
            orderGoods.setId(null);
        });
        orderGoodsService.saveBatch(orderGoodsList);
        List<OrderData> pageOrderData = orderDataMapper.getPageOrderData(userId, orderTable.getId(), null, null, null);
        return DataMessage.of(pageOrderData.get(0));
    }

    @Override
    public DataMessage<OrderData> deleteOrderByOrderId(Long orderId, Long userId) {
        OrderTable dbOrder = checkCurrentUserPermission(orderId, userId);
        dbOrder.setStatus(DataStatus.DELETED.value());
        orderTableService.updateById(dbOrder);
        List<OrderData> pageOrderData = orderDataMapper.getPageOrderData(userId, orderId, null, null, null);
        if (pageOrderData != null) {
            // ??????????????????????????????????????????????????????deleted,?????????????????????????????????(????????????????????????
            pageOrderData.get(0).setStatus(dbOrder.getStatus());
            return DataMessage.of(pageOrderData.get(0));
        }
        return DataMessage.of(null);
    }

    @Override
    public DataMessage<OrderData> updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId) {
        checkCurrentUserPermission(orderId, userId);
        OrderTable orderTable = new OrderTable();
        BeanUtils.copyProperties(orderExpressAndStatus, orderTable);
        orderTable.setId(orderId);
        orderTable.setUpdatedAt(LocalDateTime.now());
        orderTableService.updateById(orderTable);
        List<OrderData> pageOrderData = orderDataMapper.getPageOrderData(userId, orderId, null, null, null);
        return DataMessage.of(pageOrderData.get(0));
    }

    @Override
    public DataMessage<OrderData> getOrderByOrderId(Long orderId, Long userId) {
        checkCurrentUserPermission(orderId, userId);
        List<OrderData> pageOrderData = orderDataMapper.getPageOrderData(userId, orderId, null, null, null);
        return DataMessage.of(pageOrderData.get(0));
    }

    @Override
    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        int offset = (pageNum - 1) * pageSize;
        int totalNum = orderDataMapper.countPageOrderData(userId, status);
        int totalPage = (totalNum % pageSize == 0) ? totalNum / pageSize : totalNum / pageSize + 1;
        return PageMessage.of(pageNum, pageSize, totalPage, orderDataMapper.getPageOrderData(userId, null, status, pageSize, offset));
    }

    private OrderTable checkCurrentUserPermission(Long orderId, Long userId) {
        OrderTable dbOrder = orderTableService.getById(orderId);
        if (dbOrder == null) {
            throw HttpException.notFound("???????????????");
        }
        if (DataStatus.DELETED.value().equals(dbOrder.getStatus())) {
            throw HttpException.notFound("???????????????");
        }
        if (!dbOrder.getUserId().equals(userId)) {
            throw HttpException.forbidden("????????????");
        }
        return dbOrder;
    }
}
