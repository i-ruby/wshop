package work.iruby.wshop.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import work.iruby.wshop.common.entity.Goods;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    Boolean deductStock(long goodsId, int number);

}
