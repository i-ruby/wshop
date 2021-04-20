package work.iruby.wshop.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.enums.OrderStatus;
import work.iruby.wshop.order.service.impl.RpcOrderServiceImpl;

@RestController
public class index {
    RpcOrderServiceImpl orderService;

    @Autowired
    public index(RpcOrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public Object test() {
        return orderService.deleteOrderByOrderId(1L, 1L);
    }

    @GetMapping("/deleteOrderByOrderId")
    public Object deleteOrderByOrderId() {
        return orderService.deleteOrderByOrderId(1L, 1L);
    }

    @GetMapping("/updateOrderByOrderId")
    public Object updateOrderByOrderId() {
        OrderExpressAndStatus orderExpressAndStatus = new OrderExpressAndStatus();
        orderExpressAndStatus.setAddress("lala");
        orderExpressAndStatus.setExpressCompany("compa");
        orderExpressAndStatus.setExpressId("haha");
        orderExpressAndStatus.setStatus(OrderStatus.PAID.value());
        return orderService.updateOrderByOrderId(1L, orderExpressAndStatus, 1L);
    }
}
