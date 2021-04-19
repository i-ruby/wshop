package work.iruby.wshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import work.iruby.wshop.entity.ShoppingCart;
import work.iruby.wshop.entity.ShoppingCartData;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    List<ShoppingCartData> getPageShoppingCartData(long userId, Long shopId, Integer pageSize, Integer offset);

    int countShoppingCartDataByUserId(long userId);

}
