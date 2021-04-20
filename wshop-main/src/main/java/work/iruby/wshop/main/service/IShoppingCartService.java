package work.iruby.wshop.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.entity.ShoppingCart;
import work.iruby.wshop.main.entity.ShoppingCartData;

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

    PageMessage<List<ShoppingCartData>> getCurrentUserPageShoppingCartData(Integer pageNum, Integer pageSize, Long shopId);

    DataMessage<ShoppingCartData> addShoppingCartData(ShoppingCartData shoppingCartData);

    DataMessage<ShoppingCartData> deleteShoppingCartByGoodsId(Long goodsId);
}
