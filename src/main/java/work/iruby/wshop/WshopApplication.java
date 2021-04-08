package work.iruby.wshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("work.iruby.wshop.mapper")
@SpringBootApplication(scanBasePackages = {"work.iruby.wshop","org.apache.shiro"})
public class WshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WshopApplication.class, args);
    }

}
