package work.iruby.wshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.entity.DataMessage;
import work.iruby.wshop.entity.PageMessage;
import work.iruby.wshop.entity.ShoppingCartData;
import work.iruby.wshop.service.IShoppingCartService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {
    IShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shoppingCart")
    public PageMessage<List<ShoppingCartData>> index(@RequestParam(name = "pageNum") Integer pageNum,
                                                     @RequestParam(name = "pageSize") Integer pageSize) {
        return shoppingCartService.getPageShoppingCartDataCurrentUser(pageNum, pageSize, null);
    }

    /**
     * request-example
     * {
     * "goods": [
     * {
     * "id": 12345,
     * "number": 10,
     * },
     * {
     * ...
     * }
     * }
     *
     * @param shoppingCartData 只含有goodsId 和 number
     * @return 更新后的该店铺物品列表
     */
    @PostMapping("/shoppingCart")
    public DataMessage<ShoppingCartData> addShoppingCart(@RequestBody ShoppingCartData shoppingCartData) {
        return shoppingCartService.addShoppingCartData(shoppingCartData);
    }

    @DeleteMapping("/shoppingCart/{GoodsId}")
    public DataMessage<ShoppingCartData> deleteShoppingCartByGoodsId(@PathVariable(value = "GoodsId") Long GoodsId) {
        return shoppingCartService.deleteShoppingCartByGoodsId(GoodsId);
    }
}

