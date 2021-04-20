package work.iruby.wshop.main.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartGoods extends Goods {
    private int number;
}
