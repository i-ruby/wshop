package work.iruby.wshop.config;


import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
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
    public SecurityManager securityManager(MyAuthorizingRealm myAuthorizingRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSessionManager(new DefaultSessionManager());
        securityManager.setRealm(myAuthorizingRealm);
        return securityManager;
    }

//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        Map<String, String> filter = new HashMap<>();
//        filter.put("/api/code", "anon");
//        filter.put("/api/login", "anon");
//        filter.put("/**", "authc");
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filter);
//        return shiroFilterFactoryBean;
//    }

//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("/api/code", "anon");
//        chainDefinition.addPathDefinition("/api/login", "anon");
//        chainDefinition.addPathDefinition("/**", "anon");
//        return chainDefinition;
//    }
}
