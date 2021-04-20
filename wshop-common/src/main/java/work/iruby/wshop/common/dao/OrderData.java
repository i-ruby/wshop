package work.iruby.wshop.common.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import work.iruby.wshop.common.entity.Shop;

import java.util.List;

@Data
@EqualsAndHashCode
public class OrderData {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long totalPrice;
    private String address;
    private String expressCompany;
    private String expressId;
    private String status;
    private Shop shop;
    private List<ShoppingCartGoods> goods;
}
