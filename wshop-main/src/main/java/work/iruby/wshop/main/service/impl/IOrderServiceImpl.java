package work.iruby.wshop.main.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.service.UserContext;
import work.iruby.wshop.rpc.service.RpcOrderService;

import java.util.List;

@Service
public class IOrderServiceImpl {

    @DubboReference(version = "${wshop.service.version}")
    RpcOrderService rpcOrderService;

    public Object addOrder(List<GoodsIdAndNumber> goodsIdAndNumberList) {
        return null;
    }

    public Object deleteOrderByOrderId(Long orderId) {
        return null;
    }

    public Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus) {
        return null;
    }

    public PageMessage<List<OrderData>> getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status) {
        Long userId = UserContext.getCurrentUser().getId();
//        List<OrderData> orderDataList = rpcOrderService.getPageOrders(pageNum, pageSize, status, userId);
//        OrderData orderData = rpcOrderService.getOrder(userId);
        return null;
    }
}
