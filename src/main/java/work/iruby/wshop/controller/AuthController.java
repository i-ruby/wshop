package work.iruby.wshop.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.entity.LoginResponse;
import work.iruby.wshop.entity.TelAndCode;
import work.iruby.wshop.entity.User;
import work.iruby.wshop.service.SmsCodeService;
import work.iruby.wshop.service.UserContext;
import work.iruby.wshop.service.impl.MockSmsCodeService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final SmsCodeService smsCodeService;

    @Autowired
    public AuthController(MockSmsCodeService smsCodeService) {
        this.smsCodeService = smsCodeService;
    }

    @PostMapping("/code")
    public String code(@RequestBody TelAndCode telAndCode) {
        return smsCodeService.sendSmsCode(telAndCode.getTel());
    }

    @PostMapping("/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
    }

    @GetMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @GetMapping("/status")
    public LoginResponse status() {
        User currentUser = UserContext.getCurrentUser();
        return new LoginResponse(currentUser != null, currentUser);
    }

}
