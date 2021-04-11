package work.iruby.wshop.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import work.iruby.wshop.entity.TelAndCode;
import work.iruby.wshop.utils.OkHttpClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    private String url;

    @BeforeEach
    void init() {
        url = String.format("http://localhost:%d/api/v1", port);
    }

    @Test
    void user_login() throws IOException {
        TelAndCode telAndCode = new TelAndCode();
        telAndCode.setTel("18879628733");
        String code = OkHttpClientUtils.getBody(false, url + "/code", telAndCode, null, null);
        String status = OkHttpClientUtils.getBody(true, url + "/status", telAndCode, null, null);
        Assertions.assertTrue(status.contains("false"));
        telAndCode.setCode(code);
        String cookie = OkHttpClientUtils.getCookie(false, url + "/login", telAndCode, null, null);
        Map<String, String> Heads = new HashMap<>();
        Heads.put("Cookie", cookie);
        status = OkHttpClientUtils.getBody(true, url + "/status", telAndCode, Heads, null);
        Assertions.assertTrue(status.contains("true"));
        OkHttpClientUtils.getBody(false, url + "/logout", telAndCode, Heads, null);
        status = OkHttpClientUtils.getBody(true, url + "/status", telAndCode, Heads, null);
        Assertions.assertTrue(status.contains("true"));

    }

}