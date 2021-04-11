package work.iruby.wshop.config;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import work.iruby.wshop.entity.WshopUser;
import work.iruby.wshop.service.IWshopUserService;
import work.iruby.wshop.service.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    IWshopUserService wshopUserService;

    @Autowired
    public UserInterceptor(IWshopUserService wshopUserService) {
        this.wshopUserService = wshopUserService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object tel = SecurityUtils.getSubject().getPrincipal();
        if (tel != null) {
            WshopUser user = wshopUserService.getWshopUserByTel(tel.toString());
            UserContext.setCurrentUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.setCurrentUser(null);
    }

}
