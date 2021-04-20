package work.iruby.wshop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.utils.OkHttpClientUtils;
import work.iruby.wshop.main.entity.ShoppingCartData;

import java.io.IOException;
import java.util.List;

class ShoppingCartControllerTest extends BaseIntegrationTest {

    @Test
    void pageSearchTest() throws IOException {
        String cookie = loginAndGetCookie();
        int pageNum = 1;
        int pageSize = 10;
        String body = OkHttpClientUtils.getBody("get", url + "/shoppingCart?pageNum=" + pageNum + "&pageSize=" + pageSize, null, cookie);
        PageMessage<List<ShoppingCartData>> pageMessage = JSON.parseObject(body, new TypeReference<PageMessage<List<ShoppingCartData>>>() {
        });
        Assertions.assertEquals(pageMessage.getPageNum(), pageNum);
        Assertions.assertEquals(pageMessage.getPageSize(), pageSize);
        Assertions.assertEquals(pageMessage.getTotalPage(), 1);
        Assertions.assertEquals(pageMessage.getData().size(), 2);

        pageNum = 1;
        pageSize = 1;
        body = OkHttpClientUtils.getBody("get", url + "/shoppingCart?pageNum=" + pageNum + "&pageSize=" + pageSize, null, cookie);
        pageMessage = JSON.parseObject(body, new TypeReference<PageMessage<List<ShoppingCartData>>>() {
        });
        Assertions.assertEquals(pageMessage.getPageNum(), pageNum);
        Assertions.assertEquals(pageMessage.getPageSize(), pageSize);
        Assertions.assertEquals(pageMessage.getTotalPage(), 2);
        Assertions.assertEquals(pageMessage.getData().size(), 1);

        pageNum = 2;
        pageSize = 5;
        body = OkHttpClientUtils.getBody("get", url + "/shoppingCart?pageNum=" + pageNum + "&pageSize=" + pageSize, null, cookie);
        pageMessage = JSON.parseObject(body, new TypeReference<PageMessage<List<ShoppingCartData>>>() {
        });
        Assertions.assertEquals(pageMessage.getPageNum(), pageNum);
        Assertions.assertEquals(pageMessage.getPageSize(), pageSize);
        Assertions.assertEquals(pageMessage.getTotalPage(), 1);
        Assertions.assertEquals(pageMessage.getData().size(), 0);
    }

    @Test
    void addTest() throws IOException {
        String cookie = loginAndGetCookie();
        String json = "{ \"goods\": [ { \"id\": 1, \"number\": 11 }, { \"id\": 2, \"number\": 111 }] }";
        String body = OkHttpClientUtils.getBody("post", url + "/shoppingCart", JSON.parseObject(json), cookie);
        DataMessage<ShoppingCartData> pageMessage = JSON.parseObject(body, new TypeReference<DataMessage<ShoppingCartData>>() {
        });
        Assertions.assertEquals(pageMessage.getData().getGoods().size(), 2);

        Assertions.assertEquals(pageMessage.getData().getGoods().get(0).getNumber(), pageMessage.getData().getGoods().get(1).getNumber());
    }

    @Test
    void deleteTest() throws IOException {
        String cookie = loginAndGetCookie();
        String body = OkHttpClientUtils.getBody("delete", url + "/shoppingCart/4", null, cookie);
        DataMessage<ShoppingCartData> pageMessage = JSON.parseObject(body, new TypeReference<DataMessage<ShoppingCartData>>() {
        });
        Assertions.assertEquals(1, pageMessage.getData().getGoods().size());

        body = OkHttpClientUtils.getBody("delete", url + "/shoppingCart/1", null, cookie);
        pageMessage = JSON.parseObject(body, new TypeReference<DataMessage<ShoppingCartData>>() {
        });
        Assertions.assertNull(pageMessage.getData());
    }

}