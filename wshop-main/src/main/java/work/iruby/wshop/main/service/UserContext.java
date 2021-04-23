package work.iruby.wshop.main.service;

import work.iruby.wshop.common.entity.User;
import work.iruby.wshop.common.exception.HttpException;

public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static Long getCurrentUserId() {
        if (currentUser.get() != null) {
            return currentUser.get().getId();
        } else {
            throw HttpException.unAuthorized("若用户未登录");
        }
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }
}
