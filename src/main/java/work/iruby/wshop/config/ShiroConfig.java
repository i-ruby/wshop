package work.iruby.wshop.config;


import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.iruby.wshop.service.impl.MockSmsCodeService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public MyAuthorizingRealm myAuthorizingRealm(MockSmsCodeService smsCodeService) {
        return new MyAuthorizingRealm(smsCodeService);
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyAuthorizingRealm myAuthorizingRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSessionManager(new DefaultSessionManager());
        securityManager.setRealm(myAuthorizingRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filter = new HashMap<>();
        filter.put("/", "anon");
        filter.put("/api/code", "anon");
        filter.put("/api/login", "anon");
        filter.put("/api/test", "anon");
        filter.put("/api/user", "user");
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filter);
        return shiroFilterFactoryBean;
    }

}
