package work.iruby.wshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IUserService userService;

    @Autowired
    public AuthService(IUserService userService) {
        this.userService = userService;
    }

}
