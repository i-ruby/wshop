package work.iruby.wshop.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.utils.OkHttpClientUtils;

import java.io.IOException;
import java.util.List;

class OrderControllerTest extends BaseIntegrationTest {

    @Test
    void addOrder() throws IOException {
        String cookie = loginAndGetCookie();
    }

    @Test
    void deleteOrderByOrderId() throws IOException {
        String cookie = loginAndGetCookie();
    }

    @Test
    void updateOrderByOrderId() throws IOException {
        String cookie = loginAndGetCookie();
    }

    @Test
    void getCurrentUserPageOrders() throws IOException {
        String cookie = loginAndGetCookie();
        int pageNum = 1;
        int pageSize = 10;
        String status = "";
        String body = OkHttpClientUtils.getBody("get", url + "/order?pageNum=" + pageNum + "&pageSize=" + pageSize, null, cookie);
        PageMessage<List<OrderData>> pageMessage = JSON.parseObject(body, new TypeReference<PageMessage<List<OrderData>>>() {
        });
        System.out.println(pageMessage.getData());
    }
}