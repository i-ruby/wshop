package work.iruby.wshop.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.iruby.wshop.entity.TelAndCode;
import work.iruby.wshop.utils.OkHttpClientUtils;

import java.io.IOException;

class AuthControllerTest extends BaseIntegrationTest {

    @Test
    void user_login() throws IOException {
        TelAndCode telAndCode = new TelAndCode();
        telAndCode.setTel("13800000000");
        String code = OkHttpClientUtils.getBody("post", url + "/code", telAndCode);
        String status = OkHttpClientUtils.getBody("get", url + "/status", telAndCode);
        Assertions.assertTrue(status.contains("false"));
        telAndCode.setCode(code);
        String cookie = OkHttpClientUtils.getCookie("post", url + "/login", telAndCode, null);
        status = OkHttpClientUtils.getBody("get", url + "/status", telAndCode, cookie);
        Assertions.assertTrue(status.contains("true"));
        OkHttpClientUtils.getBody("post", url + "/logout", telAndCode, cookie);
        status = OkHttpClientUtils.getBody("get", url + "/status", telAndCode, cookie);
        Assertions.assertTrue(status.contains("true"));

    }


    @Test
    void test() throws IOException {
        String cookie = loginAndGetCookie();
    }

}