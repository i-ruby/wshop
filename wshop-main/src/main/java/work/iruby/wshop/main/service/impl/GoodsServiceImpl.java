package work.iruby.wshop.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.enums.DataStatus;
import work.iruby.wshop.common.entity.Goods;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.main.mapper.GoodsMapper;
import work.iruby.wshop.main.service.IGoodsService;
import work.iruby.wshop.main.service.IShopService;
import work.iruby.wshop.main.service.UserContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    private final IShopService shopService;

    @Autowired
    public GoodsServiceImpl(IShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public DataMessage<Goods> creatGood(Goods goods) {
        checkCurrentUserPermission(goods, "Forbidden 用户尝试创建非自己管理店铺的商品");
        goods.setId(null);
        goods.setStatus(DataStatus.OK.value());
        goods.setCreatedAt(LocalDateTime.now());
        goods.setUpdatedAt(LocalDateTime.now());
        save(goods);
        return DataMessage.of(goods);
    }

    @Override
    public DataMessage<Goods> deleteGoodByGoodId(Long id) {
        Goods goods = checkGoodsExited(id, "404 Not Found 若商品未找到");
        checkCurrentUserPermission(goods, "Forbidden 用户尝试删除非自己管理店铺的商品");
        goods.setStatus(DataStatus.DELETED.value());
        updateById(goods);
        return DataMessage.of(getById(id));
    }

    @Override
    public DataMessage<Goods> updateGoodByGoodId(Long id, Goods goods) {
        checkGoodsExited(id, "404 Not Found 若商品未找到");
        checkCurrentUserPermission(goods, "Forbidden 用户尝试删除非自己管理店铺的商品");
        goods.setId(id);
        goods.setCreatedAt(null);
        goods.setUpdatedAt(LocalDateTime.now());
        updateById(goods);
        return DataMessage.of(getById(id));
    }

    @Override
    public PageMessage<List<Goods>> getPageGoods(Integer pageNum, Integer pageSize, Integer shopId) {
        Page<Goods> goodsPage = new Page<>();
        goodsPage.setCurrent(pageNum);
        goodsPage.setSize(pageSize);
        QueryWrapper<Goods> queryWrapper;
        if (shopId == null) {
            queryWrapper = null;
        } else {
            queryWrapper = new QueryWrapper<Goods>().eq("shop_id", shopId);
        }

        page(goodsPage, queryWrapper);
        return PageMessage.of(pageNum, pageSize, (int) goodsPage.getPages(), goodsPage.getRecords());
    }

    @Override
    public DataMessage<Goods> getGoodByGoodId(Long id) {
        Goods goods = checkGoodsExited(id, "404 Not Found 若商品未找到");
        return DataMessage.of(goods);
    }

    private Goods checkGoodsExited(Long goodsId, String msg) {
        if (goodsId == null) {
            return new Goods();
        }
        Goods goods = getById(goodsId);
        if (goods != null && Objects.equals(goods.getStatus(), DataStatus.OK.value())) {
            return goods;
        } else {
            throw new RuntimeException(msg);
        }
    }

    private void checkCurrentUserPermission(Goods goods, String msg) {
        Shop shop = shopService.getById(goods.getShopId());
        if (shop == null || DataStatus.DELETED.value().equals(shop.getStatus()) || !Objects.equals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            throw new RuntimeException(msg);
        }
    }
}
