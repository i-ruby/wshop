package work.iruby.wshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.iruby.wshop.entity.WshopUser;
import work.iruby.wshop.mapper.WshopUserMapper;
import work.iruby.wshop.service.IWshopUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author iruby
 * @since 2021-04-07
 */
@Service
public class WshopUserServiceImpl extends ServiceImpl<WshopUserMapper, WshopUser> implements IWshopUserService {

    @Override
    public void createWshopUserIfNotExit(String tel) {
        WshopUser wshopUser = new WshopUser();
        wshopUser.setUserTel(tel);
        save(wshopUser);
    }
}
