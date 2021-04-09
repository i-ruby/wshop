package work.iruby.wshop.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/code")
    public String code(@RequestBody TelAndCode telAndCode) {
        smsCodeService.sendSmsCode(telAndCode.getTel());
        return "code";
    }

    @PostMapping("/login")
    public String login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
        return "login";
    }

    @GetMapping("/test")
    public String test() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal == null) {
            return "没有人";
        }
        return JSON.toJSONString(subject.getPrincipal());
    }


    @GetMapping("/user")
    public String user() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal == null) {
            return "没有人";
        }
        return JSON.toJSONString(subject.getPrincipal());
    }

    @Data
    public static class TelAndCode {
        String tel;
        String code;
    }
}
