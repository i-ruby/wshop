package work.iruby.wshop.main.service.impl;

import org.springframework.stereotype.Service;
import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;

import java.util.List;

@Service
public class IOrderServiceImpl {
    public Object addOrder(List<GoodsIdAndNumber> goodsIdAndNumberList, Long userId) {
        return null;
    }

    public Object deleteOrderByOrderId(Long orderId, Long userId) {
        return null;
    }

    public Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId) {
        return null;
    }

    public Object getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        return null;
    }
}
