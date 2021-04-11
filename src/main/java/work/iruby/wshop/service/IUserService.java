package work.iruby.wshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
public interface IUserService extends IService<User> {
    User getWshopUserByTel(String tel);
}
