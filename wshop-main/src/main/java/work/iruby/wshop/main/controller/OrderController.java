package work.iruby.wshop.main.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("order")
    public Object addOrder() {
        return null;
    }

}
