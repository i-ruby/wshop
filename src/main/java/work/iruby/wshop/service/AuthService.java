package work.iruby.wshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IWshopUserService wshopUserService;

    @Autowired
    public AuthService(IWshopUserService wshopUserService) {
        this.wshopUserService = wshopUserService;
    }

}
