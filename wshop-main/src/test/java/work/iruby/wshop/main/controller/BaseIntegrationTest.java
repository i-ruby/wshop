package work.iruby.wshop.main.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import work.iruby.wshop.common.dao.TelAndCode;
import work.iruby.wshop.common.utils.OkHttpClientUtils;
import work.iruby.wshop.main.WshopApplication;

import java.io.IOException;

@SpringBootTest(classes = WshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {
    @LocalServerPort
    private int port;

    protected String url;

//    @Mock
//    protected MockRpcOrderService mockRpcOrderService;

    @BeforeEach
    void setup() {
//        MockitoAnnotations.openMocks(mockRpcOrderService);
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
