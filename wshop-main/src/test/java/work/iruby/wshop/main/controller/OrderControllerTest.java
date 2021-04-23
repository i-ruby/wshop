package work.iruby.wshop.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.ShoppingCartGoods;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.common.utils.OkHttpClientUtils;

import java.io.IOException;

class OrderControllerTest extends BaseIntegrationTest {


    void addOrder() throws IOException {
        String cookie = loginAndGetCookie();
        OrderData orderData = new OrderData();
        ShoppingCartGoods goods1 = new ShoppingCartGoods();
        goods1.setId(1L);
        goods1.setNumber(3);
        ShoppingCartGoods goods2 = new ShoppingCartGoods();
        goods2.setId(2L);
        goods2.setNumber(2);
        orderData.setGoods(Lists.newArrayList(goods1, goods2));

        Shop shop = new Shop();
        shop.setId(1L);

//        when(mockRpcOrderService.addOrder(any(), any())).thenAnswer(invocation -> {
//            OrderData o = invocation.getArgument(0);
//            o.setId(12345L);
//            o.setShop(shop);
//            o.setStatus(OrderStatus.PAID.value());
//            return o;
//        });
        String body = OkHttpClientUtils.getBody("post", url + "/order", orderData, cookie);
        OrderData order = JSON.parseObject(body, new TypeReference<DataMessage<OrderData>>() {
        }).getData();

        Assertions.assertEquals(12345L, order.getId());
    }

    void deleteOrderByOrderId() throws IOException {
        String cookie = loginAndGetCookie();
    }

    void updateOrderByOrderId() throws IOException {
        String cookie = loginAndGetCookie();
    }

    void getCurrentUserPageOrders() throws IOException {
        String cookie = loginAndGetCookie();
    }
}