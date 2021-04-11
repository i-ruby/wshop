package work.iruby.wshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.entity.WshopUser;
import work.iruby.wshop.service.IWshopUserService;
import work.iruby.wshop.service.SmsCodeService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockSmsCodeService implements SmsCodeService {
    Map<String, String> telAndCode = new ConcurrentHashMap<>();
    IWshopUserService wshopUserService;

    @Autowired
    public MockSmsCodeService(IWshopUserService wshopUserService) {
        this.wshopUserService = wshopUserService;
    }

    @Override
    public String sendSmsCode(String tel) {

        WshopUser wshopUser = wshopUserService.getOne(new QueryWrapper<WshopUser>().eq("user_tel", tel));
        if (wshopUser == null) {
            wshopUser = new WshopUser();
            wshopUser.setUserTel(tel);
            wshopUserService.save(wshopUser);
        }
        String code = "000000";
        telAndCode.put(tel, code);
        return code;
    }

    @Override
    public String getCorrectSmsCode(String tel) {
        return telAndCode.get(tel);
    }
}
