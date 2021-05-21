package work.iruby.wshop.main.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import work.iruby.wshop.main.service.SmsCodeService;

@Component
public class UserRealm extends AuthorizingRealm {

    private final SmsCodeService smsCodeService;

    public UserRealm(SmsCodeService smsCodeService) {
        this.smsCodeService = smsCodeService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String tel = (String) authenticationToken.getPrincipal();
        String correctCode = smsCodeService.getCorrectSmsCode(tel);
        return new SimpleAuthenticationInfo(tel, correctCode, getName());
    }
}
