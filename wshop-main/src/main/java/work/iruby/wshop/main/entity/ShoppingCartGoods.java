package work.iruby.wshop.main.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import work.iruby.wshop.common.entity.Goods;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartGoods extends Goods {
    private int number;
}
