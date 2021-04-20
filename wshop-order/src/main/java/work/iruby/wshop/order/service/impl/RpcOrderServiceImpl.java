package work.iruby.wshop.order.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.entity.OrderTable;
import work.iruby.wshop.common.enums.DataStatus;
import work.iruby.wshop.order.mapper.OrderDataMapper;
import work.iruby.wshop.order.service.IOrderGoodsService;
import work.iruby.wshop.order.service.IOrderTableService;
import work.iruby.wshop.rpc.service.RpcOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public DataMessage<OrderData> addOrder(List<GoodsIdAndNumber> goodsIdAndNumberList, Long userId) {

        System.out.println("1");
        return null;
    }

    @Override
    public DataMessage<OrderData> deleteOrderByOrderId(Long orderId, Long userId) {
        OrderTable dbOrder = checkCurrentUserPermission(orderId, userId);
        dbOrder.setStatus(DataStatus.DELETED.value());
        orderTableService.updateById(dbOrder);
        List<OrderData> pageOrderData = orderDataMapper.getPageOrderData(userId, orderId, null, null, null);
        if (pageOrderData != null) {
            // 普通来说,逻辑删除怎么可能会和业务状态一个字段
            List<OrderData> collect = pageOrderData.stream().filter(orderData -> !DataStatus.DELETED.value().equals(orderData.getStatus())).collect(Collectors.toList());
            if (collect.size() != 0) {
                return DataMessage.of(pageOrderData.get(0));
            }
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
    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        int offset = (pageNum - 1) * pageSize;
        int count = orderDataMapper.countPageOrderData(userId, status);
        int totalSize = count / pageSize;
        return PageMessage.of(pageNum, pageSize, totalSize, orderDataMapper.getPageOrderData(userId, null, status, pageSize, offset));
    }

    private OrderTable checkCurrentUserPermission(Long orderId, Long userId) {
        OrderTable dbOrder = orderTableService.getById(orderId);
        if (dbOrder == null) {
            throw new RuntimeException("订单未找到");
        }
        if (!dbOrder.getUserId().equals(userId)) {
            throw new RuntimeException("无权访问");
        }
        return dbOrder;
    }
}
