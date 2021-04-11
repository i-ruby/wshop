package work.iruby.wshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.entity.DataMessage;
import work.iruby.wshop.entity.Goods;
import work.iruby.wshop.entity.PageMessage;
import work.iruby.wshop.entity.Shop;
import work.iruby.wshop.mapper.GoodsMapper;
import work.iruby.wshop.service.IGoodsService;
import work.iruby.wshop.service.IShopService;
import work.iruby.wshop.service.UserContext;

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

    private IShopService shopService;

    @Autowired
    public GoodsServiceImpl(IShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public DataMessage<Goods> creatGood(Goods goods) {
        Shop shop = shopService.getById(goods.getShopId());
        if (shop != null && Objects.equals(shop.getOwnerUserId(), UserContext.getCurrentUser().getId())) {
            goods.setId(null);
            goods.setCreatedAt(null);
            goods.setUpdatedAt(null);
            save(goods);
        } else {
            throw new RuntimeException("不是你家店铺");
        }
        // 我觉得我为了获得两个时间多查一遍就是憨批(
        return DataMessage.of(getById(goods.getId()));
    }

    @Override
    public DataMessage<Goods> deleteGood(Long id) {
        Goods goods = getById(id);
        Long shopId = goods.getShopId();
        update();
        return null;
    }

    @Override
    public DataMessage<Goods> updateGood(Goods goods) {
        return null;
    }

    @Override
    public DataMessage<Goods> getPageGoods(int pageNum, int pageSize, int shopId) {
        return null;
    }

    @Override
    public PageMessage<List<Goods>> getGoodByGoodId(Long id) {
        return null;
    }
}
