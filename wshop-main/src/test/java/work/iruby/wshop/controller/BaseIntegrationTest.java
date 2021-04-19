package work.iruby.wshop.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import work.iruby.wshop.entity.TelAndCode;
import work.iruby.wshop.utils.OkHttpClientUtils;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {
    @LocalServerPort
    private int port;

    protected String url;

    @BeforeEach
    void setup() {
        url = String.format("http://localhost:%d/api/v1", port);
        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://121.4.73.4:3307/wshop", "root", "iruby234").load();
        flyway.clean();
        flyway.migrate();

    }


    protected String loginAndGetCookie(String tel) throws IOException {
        TelAndCode telAndCode = new TelAndCode();
        telAndCode.setTel(tel);
        String code = OkHttpClientUtils.getBody("post", url + "/code", telAndCode);
        telAndCode.setCode(code);
        return OkHttpClientUtils.getCookie("post", url + "/login", telAndCode, null);
    }

    protected String loginAndGetCookie() throws IOException {
        return loginAndGetCookie("13800000000");
    }

}