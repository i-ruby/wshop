package work.iruby.wshop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.iruby.wshop.entity.DataMessage;
import work.iruby.wshop.entity.Goods;
import work.iruby.wshop.enumerations.DataStatus;
import work.iruby.wshop.utils.OkHttpClientUtils;

import java.io.IOException;
import java.util.List;

class GoodsControllerTest extends BaseIntegrationTest {

    @Test
    public void creatGoods() throws IOException {
        String cookie = loginAndGetCookie();
        Goods goods = new Goods();
        goods.setName("汽水");
        goods.setDescription("纯天然汽水");
        goods.setDetails("纯天然好汽水");
        goods.setImgUrl("http://img");
        goods.setPrice(100L);
        goods.setStock(2);
        goods.setShopId(1L);
        // 插入
        String body = OkHttpClientUtils.getBody("post", url + "/goods", goods, cookie);
        DataMessage<Goods> message = JSON.parseObject(body, new TypeReference<DataMessage<Goods>>() {
        });
        Assertions.assertNotNull(message.getData().getId());
        // 查询单个
        body = OkHttpClientUtils.getBody("patch", url + "/goods/" + message.getData().getId(), goods, cookie);
        message = JSON.parseObject(body, new TypeReference<DataMessage<Goods>>() {
        });
        Goods DbGoods = message.getData();
        DbGoods.setCreatedAt(null);
        DbGoods.setUpdatedAt(null);
        Assertions.assertEquals(DbGoods, goods);
        // 更新
        goods.setName("new name");
        goods.setDescription("new des");
        goods.setDetails("new det");
        goods.setImgUrl("new url");
        goods.setPrice(10L);
        goods.setStock(10);
        body = OkHttpClientUtils.getBody("patch", url + "/goods/" + message.getData().getId(), goods, cookie);
        message = JSON.parseObject(body, new TypeReference<DataMessage<Goods>>() {
        });
        DbGoods = message.getData();
        DbGoods.setUpdatedAt(null);
        Assertions.assertEquals(DbGoods, goods);
        // 查询所有
        body = OkHttpClientUtils.getBody("get", url + "/goods?pageNum=1&pageSize=4", null, cookie);
        DataMessage<List<Goods>> pageDataMessage = JSON.parseObject(body, new TypeReference<DataMessage<List<Goods>>>() {
        });
        List<Goods> goodsList = pageDataMessage.getData();
        Assertions.assertTrue(goodsList.size() <= 4);
        body = OkHttpClientUtils.getBody("get", url + "/goods?pageNum=1&pageSize=4&shopId=1", null, cookie);
        pageDataMessage = JSON.parseObject(body, new TypeReference<DataMessage<List<Goods>>>() {
        });
        goodsList = pageDataMessage.getData();
        if (goodsList != null && goodsList.size() != 0) {
            for (Goods g : goodsList) {
                Assertions.assertEquals(g.getShopId(), 1L);
            }
        }
        // 删除
        body = OkHttpClientUtils.getBody("delete", url + "/goods/" + message.getData().getId(), null, cookie);
        message = JSON.parseObject(body, new TypeReference<DataMessage<Goods>>() {
        });
        Assertions.assertEquals(message.getData().getStatus(), DataStatus.DELETED.value());
    }

}