package work.iruby.wshop.common.dao;

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
public class OrderExpressAndStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

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

}
