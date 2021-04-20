package work.iruby.wshop.order.entity;

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
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long shopId;

    private Long totalPrice;

    private String address;

    private String expressCompany;

    private String expressId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
