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

    DataMessage<Goods> deleteGood(Long id);

    DataMessage<Goods> updateGood(Goods goods);

    DataMessage<Goods> getPageGoods(int pageNum, int pageSize, int shopId);

    PageMessage<List<Goods>> getGoodByGoodId(Long id);
}
