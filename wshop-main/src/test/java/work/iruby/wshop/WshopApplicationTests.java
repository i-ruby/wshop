package work.iruby.wshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import work.iruby.wshop.main.WshopApplication;

@SpringBootTest(classes = WshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WshopApplicationTests {

    @Test
    void contextLoads() {
    }

}
