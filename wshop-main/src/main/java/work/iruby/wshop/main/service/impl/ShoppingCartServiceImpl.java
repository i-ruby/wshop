package work.iruby.wshop.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.dao.ShoppingCartGoods;
import work.iruby.wshop.common.entity.ShoppingCart;
import work.iruby.wshop.common.enums.DataStatus;
import work.iruby.wshop.common.exception.HttpException;
import work.iruby.wshop.main.entity.ShoppingCartData;
import work.iruby.wshop.main.mapper.ShoppingCartMapper;
import work.iruby.wshop.main.service.IGoodsService;
import work.iruby.wshop.main.service.IShoppingCartService;
import work.iruby.wshop.main.service.UserContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    private final IGoodsService goodsService;

    @Autowired
    public ShoppingCartServiceImpl(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public PageMessage<List<ShoppingCartData>> getCurrentUserPageShoppingCartData(Integer pageNum, Integer pageSize, Long shopId) {
        int offset = (pageNum - 1) * pageSize;
        long userId = UserContext.getCurrentUser().getId();
        List<ShoppingCartData> pageShoppingCartData = getBaseMapper().getPageShoppingCartData(userId, shopId, pageSize, offset);
        int totalNum = getBaseMapper().countShoppingCartDataByUserId(userId);
        int totalPage = (totalNum % pageSize == 0) ? totalNum / pageSize : totalNum / pageSize + 1;
        return PageMessage.of(pageNum, pageSize, totalPage, pageShoppingCartData);
    }

    @Override
    public DataMessage<ShoppingCartData> addShoppingCartData(ShoppingCartData shoppingCartData) {
        List<ShoppingCartGoods> shoppingCartGoods = shoppingCartData.getGoods();
        HashSet<Long> shopIdSet = new HashSet<>();
        shoppingCartGoods.forEach(goods -> shopIdSet.add(goodsService.getById(goods.getId()).getShopId()));
        if (shopIdSet.size() > 1) {
            throw HttpException.badRequest("禁止添加不同店铺物品");
        }
        if (shopIdSet.size() == 0) {
            return DataMessage.of(null);
        }
        Long shopId = shopIdSet.stream().findFirst().get();

        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        shoppingCartGoods.forEach(goods -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setGoodsId(goods.getId());
            if (goodsService.getById(goods.getId()) == null) {
                throw HttpException.badRequest("商品未找到");
            }
            shoppingCart.setShopId(shopId);
            shoppingCart.setUserId(UserContext.getCurrentUser().getId());
            shoppingCart.setStatus(DataStatus.OK.value());
            ShoppingCart dbShoppingCart = getOne(new QueryWrapper<>(shoppingCart));
            if (dbShoppingCart != null) {
                shoppingCart.setId(dbShoppingCart.getId());
                shoppingCart.setNumber(goods.getNumber() + dbShoppingCart.getNumber());
            } else {
                shoppingCart.setNumber(goods.getNumber());
            }
            shoppingCarts.add(shoppingCart);
        });
        saveOrUpdateBatch(shoppingCarts);
        List<ShoppingCartData> pageShoppingCartData = getBaseMapper().getPageShoppingCartData(UserContext.getCurrentUser().getId(), shopId, null, null);
        if (pageShoppingCartData == null || pageShoppingCartData.size() == 0) {
            return DataMessage.of(null);
        } else {
            return DataMessage.of(pageShoppingCartData.get(0));
        }

    }

    @Override
    public DataMessage<ShoppingCartData> deleteShoppingCartByGoodsId(Long goodsId) {
        long userId = UserContext.getCurrentUser().getId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setStatus(DataStatus.OK.value());
        shoppingCart.setGoodsId(goodsId);
        ShoppingCart dbShoppingCart = getOne(new QueryWrapper<>(shoppingCart));
        if (dbShoppingCart == null) {
            return DataMessage.of(null);
        }
        dbShoppingCart.setStatus(DataStatus.DELETED.value());
        updateById(dbShoppingCart);
        List<ShoppingCartData> pageShoppingCartData = getBaseMapper().getPageShoppingCartData(UserContext.getCurrentUser().getId(), dbShoppingCart.getShopId(), null, null);
        if (pageShoppingCartData == null || pageShoppingCartData.size() == 0) {
            return DataMessage.of(null);
        } else {
            return DataMessage.of(pageShoppingCartData.get(0));
        }
    }
}
