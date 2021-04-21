package work.iruby.wshop.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author iruby
 * @since 2021-04-11
 */
@Data
@EqualsAndHashCode
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 状态 ok正常 deleted 已经删除
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


}
