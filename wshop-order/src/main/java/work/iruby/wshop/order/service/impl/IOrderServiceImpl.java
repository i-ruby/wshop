package work.iruby.wshop.order.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.rpc.service.IOrderService;

import java.util.List;

@DubboService(version = "${wshop.service.version}")
public class IOrderServiceImpl implements IOrderService {
    @Override
    public Object addOrder(List<GoodsIdAndNumber> goodsIdAndNumberList, Long userId) {
        System.out.println("1");
        return null;
    }

    @Override
    public Object deleteOrderByOrderId(Long orderId, Long userId) {
        System.out.println("2");
        return null;
    }

    @Override
    public Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId) {
        System.out.println("3");
        return null;
    }

    @Override
    public Object getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        System.out.println("3");
        return null;
    }
}
