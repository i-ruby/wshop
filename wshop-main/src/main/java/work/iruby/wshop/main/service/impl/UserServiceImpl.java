package work.iruby.wshop.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.wshop.common.entity.User;
import work.iruby.wshop.main.mapper.UserMapper;
import work.iruby.wshop.main.service.IUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User getUserByTel(String tel) {
        return getOne(new QueryWrapper<User>().eq("tel", tel));
    }
}
