package work.iruby.wshop.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import work.iruby.wshop.service.SmsCodeService;


public class MyAuthorizingRealm extends AuthorizingRealm {

    private final SmsCodeService smsCodeService;

    @Autowired
    public MyAuthorizingRealm(SmsCodeService smsCodeService) {
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
