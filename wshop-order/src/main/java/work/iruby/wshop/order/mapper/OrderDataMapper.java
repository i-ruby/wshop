package work.iruby.wshop.order.mapper;

import work.iruby.wshop.common.dao.OrderData;

import java.util.List;

public interface OrderDataMapper {
    List<OrderData> getPageOrderData(Long userId, Long orderId, String status, Integer limit, Integer offset);

    Integer countPageOrderData(Long userId, String status);
}
