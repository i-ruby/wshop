package work.iruby.wshop.controller;

import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.service.SmsCodeService;
import work.iruby.wshop.service.impl.MockSmsCodeService;

@RestController
@RequestMapping("/api")
public class AuthControlller {

    private final SmsCodeService smsCodeService;

    @Autowired
    public AuthControlller(MockSmsCodeService smsCodeService) {
        this.smsCodeService = smsCodeService;
    }

//    @PostMapping("/code")
//    public void code(@RequestBody TelAndCode telAndCode) {
//        smsCodeService.sendSmsCode(telAndCode.getTel());
//    }

    @PostMapping("/code")
    public void code() {
    }

    @PostMapping("/login")
    public String login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
        SecurityUtils.getSubject().login(token);
        return "login";
    }

    @Data
    public static class TelAndCode {
        String tel;
        String code;
    }
}
