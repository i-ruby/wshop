package work.iruby.wshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.common.dao.DataMessage;
import work.iruby.common.dao.PageMessage;
import work.iruby.wshop.entity.ShoppingCart;
import work.iruby.wshop.entity.ShoppingCartData;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    PageMessage<List<ShoppingCartData>> getPageShoppingCartDataCurrentUser(Integer pageNum, Integer pageSize, Long shopId);

    DataMessage<ShoppingCartData> addShoppingCartData(ShoppingCartData shoppingCartData);

    DataMessage<ShoppingCartData> deleteShoppingCartByGoodsId(Long goodsId);
}
