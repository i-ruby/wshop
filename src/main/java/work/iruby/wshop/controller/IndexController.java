package work.iruby.wshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.service.IWshopUserService;

@RestController
public class IndexController {

    @Autowired
    private IWshopUserService wshopUserService;

    @GetMapping("/")
    public String index() {
        wshopUserService.createWshopUserIfNotExit("18879628733");
        return "index";
    }
}
