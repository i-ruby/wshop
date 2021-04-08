package work.iruby.wshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.iruby.wshop.entity.WshopUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author iruby
 * @since 2021-04-07
 */
public interface IWshopUserService extends IService<WshopUser> {

    public void createWshopUserIfNotExit(String tel);
}
