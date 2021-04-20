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
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.entity.Goods;
import work.iruby.wshop.main.service.IGoodsService;

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
        return goodsService.deleteGoodByGoodId(id);
    }

    @PatchMapping("/goods/{id}")
    public DataMessage<Goods> updateGood(@PathVariable(value = "id") Long id, @RequestBody Goods goods) {
        return goodsService.updateGoodByGoodId(id, goods);
    }

    @GetMapping("/goods")
    public PageMessage<List<Goods>> getPageGoods(@RequestParam(name = "pageNum") Integer pageNum,
                                                 @RequestParam(name = "pageSize") Integer pageSize,
                                                 @RequestParam(name = "shopId", required = false) Integer shopId) {
        return goodsService.getPageGoods(pageNum, pageSize, shopId);
    }

    @GetMapping("/goods/{id}")
    public DataMessage<Goods> getGoodByGoodId(@PathVariable(value = "id") Long id) {
        return goodsService.getGoodByGoodId(id);
    }

}

