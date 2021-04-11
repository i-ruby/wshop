package work.iruby.wshop.config;


import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import work.iruby.wshop.service.IUserService;
import work.iruby.wshop.service.impl.MockSmsCodeService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements WebMvcConfigurer {

    IUserService userService;

    @Autowired
    public ShiroConfig(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(userService));
    }

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
        filter.put("/api/code", "anon");
        filter.put("/api/login", "anon");
        filter.put("/api/status", "anon");
        filter.put("/api/logout", "user");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filter);
        return shiroFilterFactoryBean;
    }

}
