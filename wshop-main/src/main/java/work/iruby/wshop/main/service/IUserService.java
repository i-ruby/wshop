package work.iruby.wshop.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.common.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface IUserService extends IService<User> {
    User getUserByTel(String tel);
}
