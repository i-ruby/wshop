package work.iruby.wshop.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import work.iruby.wshop.common.entity.Shop;

import java.util.List;

@Data
@EqualsAndHashCode
public class ShoppingCartData {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private transient int id;

    private Shop shop;

    private List<ShoppingCartGoods> goods;
}
