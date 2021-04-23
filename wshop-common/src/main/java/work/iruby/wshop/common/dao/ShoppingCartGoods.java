package work.iruby.wshop.common.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import work.iruby.wshop.common.entity.Goods;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartGoods extends Goods {
    private int number;
}
