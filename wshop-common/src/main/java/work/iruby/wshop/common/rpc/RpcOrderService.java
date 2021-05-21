package work.iruby.wshop.common.rpc;

import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-18
 */
public interface RpcOrderService {

    DataMessage<OrderData> addOrder(OrderData order, Long userId);

    DataMessage<OrderData> deleteOrderByOrderId(Long orderId, Long userId);

    DataMessage<OrderData> updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId);

    DataMessage<OrderData> getOrderByOrderId(Long orderId, Long userId);

    PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId);

}
