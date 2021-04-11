package work.iruby.wshop.service;

import work.iruby.wshop.entity.WshopUser;

public class UserContext {
    private static ThreadLocal<WshopUser> currentUser = new ThreadLocal<>();

    public static WshopUser getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(WshopUser wshopUser) {
        currentUser.set(wshopUser);
    }
}
