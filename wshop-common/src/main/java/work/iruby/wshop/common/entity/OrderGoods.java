package work.iruby.wshop.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author iruby
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 价格 单位分
     */
    private Long number;


}
