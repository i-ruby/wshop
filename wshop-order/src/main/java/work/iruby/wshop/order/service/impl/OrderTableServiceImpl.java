package work.iruby.wshop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.wshop.order.entity.OrderTable;
import work.iruby.wshop.order.mapper.OrderTableMapper;
import work.iruby.wshop.order.service.IOrderTableService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-19
 */
@Service
public class OrderTableServiceImpl extends ServiceImpl<OrderTableMapper, OrderTable> implements IOrderTableService {

}
