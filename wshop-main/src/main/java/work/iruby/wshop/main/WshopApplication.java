package work.iruby.wshop.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("work.iruby.wshop.main.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class WshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WshopApplication.class, args);
    }

}
