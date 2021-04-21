package work.iruby.wshop.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return null;
    }
}
