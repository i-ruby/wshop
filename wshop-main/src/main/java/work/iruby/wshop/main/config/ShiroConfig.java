package work.iruby.wshop.main.config;


import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import work.iruby.wshop.main.service.impl.MockSmsCodeService;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements WebMvcConfigurer {

    UserInterceptor userInterceptor;
    MyShiroLoginFilter myShiroLoginFilter;

    @Autowired
    public ShiroConfig(UserInterceptor userInterceptor, MyShiroLoginFilter myShiroLoginFilter) {
        this.userInterceptor = userInterceptor;
        this.myShiroLoginFilter = myShiroLoginFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
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
        Map<String, String> pattern = new LinkedHashMap<>();
        pattern.put("/", "anon");
        pattern.put("/api/v1/code", "anon");
        pattern.put("/api/v1/login", "anon");
        pattern.put("/api/v1/status", "anon");
        pattern.put("/**", "user");
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("myShiroLoginFilter", myShiroLoginFilter);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(pattern);
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

}
