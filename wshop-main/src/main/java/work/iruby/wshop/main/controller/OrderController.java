package work.iruby.wshop.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.common.dao.GoodsIdAndNumber;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.main.service.UserContext;
import work.iruby.wshop.main.service.impl.IOrderServiceImpl;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author iruby
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    IOrderServiceImpl orderService;

    @Autowired
    public OrderController(IOrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("order")
    public Object addOrder(@RequestBody List<GoodsIdAndNumber> goodsIdAndNumberList) {
        Long UserId = UserContext.getCurrentUser().getId();
        return orderService.addOrder(goodsIdAndNumberList, UserId);
    }

    @DeleteMapping("order/{id}")
    public Object deleteOrderByOrderId(@PathVariable(value = "id") Long orderId) {
        Long UserId = UserContext.getCurrentUser().getId();
        return orderService.deleteOrderByOrderId(orderId, UserId);
    }

    @PatchMapping("order/{id}")
    public Object updateOrderByOrderId(@PathVariable(value = "id") Long orderId, @RequestBody OrderExpressAndStatus orderExpressAndStatus) {
        Long UserId = UserContext.getCurrentUser().getId();
        return orderService.updateOrderByOrderId(orderId, orderExpressAndStatus, UserId);
    }

    @GetMapping("order")
    public Object getCurrentUserPageOrders(@RequestParam(name = "pageNum") Integer pageNum,
                                           @RequestParam(name = "pageSize") Integer pageSize,
                                           @RequestParam(name = "status", required = false) String status) {
        Long UserId = UserContext.getCurrentUser().getId();
        return orderService.getCurrentUserPageOrders(pageNum, pageSize, status, UserId);
    }

}

