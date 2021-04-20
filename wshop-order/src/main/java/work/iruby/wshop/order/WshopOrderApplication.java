package work.iruby.wshop.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("work.iruby.wshop.order.mapper")
@SpringBootApplication
public class WshopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WshopOrderApplication.class, args);
    }

}
