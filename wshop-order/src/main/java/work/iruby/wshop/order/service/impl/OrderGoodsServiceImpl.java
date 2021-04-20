package work.iruby.wshop.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.entity.OrderGoods;
import work.iruby.wshop.order.mapper.OrderGoodsMapper;
import work.iruby.wshop.order.service.IOrderGoodsService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-19
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {

}
