package work.iruby.wshop.main.mock;

import org.mockito.Mock;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.rpc.RpcOrderService;

import java.util.List;

//@DubboService(version = "${wshop.service.version}")
public class MockRpcOrderService implements RpcOrderService {

    @Mock
    private RpcOrderService rpcOrderService;

    public MockRpcOrderService(RpcOrderService rpcOrderService) {
        this.rpcOrderService = rpcOrderService;
    }

    @Override
    public DataMessage<OrderData> addOrder(OrderData order, Long userId) {
        return rpcOrderService.addOrder(order, userId);
    }

    @Override
    public DataMessage<OrderData> deleteOrderByOrderId(Long orderId, Long userId) {
        return rpcOrderService.deleteOrderByOrderId(orderId, userId);
    }

    @Override
    public DataMessage<OrderData> updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId) {
        return rpcOrderService.updateOrderByOrderId(orderId, orderExpressAndStatus, userId);
    }

    @Override
    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        return rpcOrderService.getCurrentUserPageOrders(pageNum, pageSize, status, userId);
    }
}
