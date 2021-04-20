package work.iruby.wshop.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.entity.Shop;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
public interface IShopService extends IService<Shop> {

    DataMessage<Shop> creatShop(Shop shop);

    DataMessage<Shop> deleteShopByShopId(Long shopId);

    DataMessage<Shop> updateShopByShopId(Long shopId, Shop shop);

    PageMessage<List<Shop>> getShopsCurrentUser(Integer pageNum, Integer pageSize);

    DataMessage<Shop> getShopByShopId(Long shopId);
}
