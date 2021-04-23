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
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long userId;

    /**
     * 用户名
     */
    private Long shopId;

    /**
     * 价格，单位分
     */
    private Long totalPrice;

    /**
     * 地址
     */
    private String address;

    /**
     * 物流商
     */
    private String expressCompany;

    /**
     * 物流id
     */
    private String expressId;

    /**
     * PENDING 待付款 PAID 已付款 DELIVERED 物流中 RECEIVED 已收货
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
