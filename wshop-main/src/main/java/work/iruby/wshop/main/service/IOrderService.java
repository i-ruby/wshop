package work.iruby.wshop.main.service;

import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;

import java.util.List;

public interface IOrderService {
    Object addOrder(OrderData order);

    Object deleteOrderByOrderId(Long orderId);

    Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus);

    PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status);

    Long deductStockAndCalTotalPrice(OrderData order);
}
