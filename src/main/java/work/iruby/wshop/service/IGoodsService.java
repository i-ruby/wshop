package work.iruby.wshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.entity.DataMessage;
import work.iruby.wshop.entity.Goods;
import work.iruby.wshop.entity.PageMessage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface IGoodsService extends IService<Goods> {

    DataMessage<Goods> creatGood(Goods goods);

    DataMessage<Goods> deleteGoodByGoodId(Long id);

    DataMessage<Goods> updateGoodByGoodId(Long id, Goods goods);

    PageMessage<List<Goods>> getPageGoods(Integer pageNum, Integer pageSize, Integer shopId);

    DataMessage<Goods> getGoodByGoodId(Long id);
}
