package work.iruby.wshop.rpc.service;

import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-18
 */
public interface IOrderService {

    Object addOrder(List<GoodsIdAndNumber> goodsIdAndNumberList, Long userId);

    Object deleteOrderByOrderId(Long orderId, Long userId);

    Object updateOrderByOrderId(Long orderId, OrderExpressAndStatus orderExpressAndStatus, Long userId);

    Object getCurrentUserPageOrders(Integer pageNum, Integer pageSize, String status, Long userId);
}
