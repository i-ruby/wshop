package work.iruby.wshop.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.entity.Goods;

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
