package work.iruby.wshop.controller;


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
import work.iruby.wshop.entity.DataMessage;
import work.iruby.wshop.entity.Goods;
import work.iruby.wshop.entity.PageMessage;
import work.iruby.wshop.service.IGoodsService;

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
public class GoodsController {

    private final IGoodsService goodsService;

    @Autowired
    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/goods")
    public DataMessage<Goods> creatGood(@RequestBody Goods goods) {
        return goodsService.creatGood(goods);
    }

    @DeleteMapping("/goods/{id}")
    public DataMessage<Goods> deleteGood(@PathVariable(value = "id") Long id) {
        return goodsService.deleteGood(id);
    }

    @PatchMapping("/goods")
    public DataMessage<Goods> updateGood(@RequestBody Goods goods) {
        return goodsService.updateGood(goods);
    }

    @GetMapping("/goods")
    public DataMessage<Goods> getPageGoods(@RequestParam(name = "pageNum") int pageNum,
                                           @RequestParam(name = "pageSize") int pageSize,
                                           @RequestParam(name = "shopId", required = false) int shopId) {
        return goodsService.getPageGoods(pageNum, pageSize, shopId);
    }

    @GetMapping("/goods/{id}")
    public PageMessage<List<Goods>> getGoodByGoodId(@PathVariable(value = "id") Long id) {
        return goodsService.getGoodByGoodId(id);
    }

}

