package work.iruby.wshop.main.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.main.service.IShopService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/api/v1")
public class ShopController {

    private final IShopService shopService;

    public ShopController(IShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/shop")
    public DataMessage<Shop> creatShop(@RequestBody Shop shop) {
        return shopService.creatShop(shop);
    }

    @DeleteMapping("/shop/{id}")
    public DataMessage<Shop> deleteShopByShopId(@PathVariable(value = "id") Long shopId) {
        return shopService.deleteShopByShopId(shopId);
    }

    @PatchMapping("/shop/{id}")
    public DataMessage<Shop> updateShopByShopId(@PathVariable(value = "id") Long shopId, @RequestBody Shop shop) {
        return shopService.updateShopByShopId(shopId, shop);
    }

    @GetMapping("/shop")
    public PageMessage<List<Shop>> getShopsCurrentUser(@RequestParam(name = "pageNum") Integer pageNum,
                                                       @RequestParam(name = "pageSize") Integer pageSize) {
        return shopService.getShopsCurrentUser(pageNum, pageSize);
    }

    @GetMapping("/shop/{id}")
    public DataMessage<Shop> getShopByShopId(@PathVariable(value = "id") Long shopId) {
        return shopService.getShopByShopId(shopId);
    }
}

