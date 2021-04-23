package work.iruby.wshop.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.common.enums.DataStatus;
import work.iruby.wshop.common.utils.OkHttpClientUtils;

import java.io.IOException;
import java.util.List;

class ShopControllerTest extends BaseIntegrationTest {

    @Test
    void creatShop() throws IOException {
        String cookie = loginAndGetCookie();
        Shop shop = new Shop();
        shop.setName("我的店铺");
        shop.setDescription("我的苹果专卖店");
        shop.setImgUrl("https://img.url");
        String body = OkHttpClientUtils.getBody("post", url + "/shop", shop, cookie);
        DataMessage<Shop> shopDataMessage = JSON.parseObject(body, new TypeReference<DataMessage<Shop>>() {
        });
        Shop dbShop = shopDataMessage.getData();
        Assertions.assertNotNull(dbShop.getId());
        Assertions.assertEquals(shop.getName(), dbShop.getName());
        Assertions.assertEquals(shop.getDescription(), dbShop.getDescription());
        Assertions.assertEquals(shop.getImgUrl(), dbShop.getImgUrl());
    }

    @Test
    void deleteShopByShopId() throws IOException {
        String cookie = loginAndGetCookie();
        int shopId = 1;
        String body = OkHttpClientUtils.getBody("delete", url + "/shop/" + shopId, cookie);
        Shop dbShop = JSON.parseObject(body, new TypeReference<DataMessage<Shop>>() {
        }).getData();
        Assertions.assertEquals(DataStatus.DELETED.value(), dbShop.getStatus());
    }

    @Test
    void updateShopByShopId() throws IOException {
        String cookie = loginAndGetCookie();
        Long shopId = 1L;
        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setName("new name");
        shop.setDescription("new description");
        shop.setImgUrl("new url");
        String body = OkHttpClientUtils.getBody("patch", url + "/shop/" + shopId, shop, cookie);
        Shop dbShop = JSON.parseObject(body, new TypeReference<DataMessage<Shop>>() {
        }).getData();
        Assertions.assertEquals("new name", dbShop.getName());
        Assertions.assertEquals("new description", dbShop.getDescription());
        Assertions.assertEquals("new url", dbShop.getImgUrl());
    }

    @Test
    void getShopsCurrentUser() throws IOException {
        String cookie = loginAndGetCookie();
        int pageNum = 1;
        int pageSize = 10;
        String body = OkHttpClientUtils.getBody("get", url + "/shop?pageNum=" + pageNum + "&pageSize=" + pageSize, cookie);
        PageMessage<List<Shop>> pageMessage = JSON.parseObject(body, new TypeReference<PageMessage<List<Shop>>>() {
        });
        Assertions.assertEquals(pageMessage.getPageNum(), pageNum);
        Assertions.assertEquals(pageMessage.getPageSize(), pageSize);
        List<Shop> shopList = pageMessage.getData();
        shopList.forEach(shop -> Assertions.assertEquals(1, shop.getOwnerUserId()));
    }

    @Test
    void getShopByShopId() throws IOException {
        String cookie = loginAndGetCookie();
        int shopId = 1;
        String body = OkHttpClientUtils.getBody("get", url + "/shop/" + shopId, cookie);
        Shop dbShop = JSON.parseObject(body, new TypeReference<DataMessage<Shop>>() {
        }).getData();
        Assertions.assertNotNull(dbShop);
    }
}