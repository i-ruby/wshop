package work.iruby.wshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.wshop.entity.Shop;
import work.iruby.wshop.mapper.ShopMapper;
import work.iruby.wshop.service.IShopService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

}
