package work.iruby.wshop.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.iruby.wshop.main.entity.User;
import work.iruby.wshop.main.service.IUserService;
import work.iruby.wshop.main.service.SmsCodeService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockSmsCodeService implements SmsCodeService {
    Map<String, String> telAndCode = new ConcurrentHashMap<>();
    IUserService userService;

    @Autowired
    public MockSmsCodeService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String sendSmsCode(String tel) {

        User user = userService.getOne(new QueryWrapper<User>().eq("tel", tel));
        if (user == null) {
            user = new User();
            user.setTel(tel);
            userService.save(user);
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
