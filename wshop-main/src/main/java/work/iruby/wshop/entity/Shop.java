package work.iruby.wshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode
public class Shop {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺名
     */
    private String name;

    /**
     * 店铺描述
     */
    private String description;

    /**
     * 店铺图片url
     */
    private String imgUrl;

    /**
     * 店主用户id
     */
    private Long ownerUserId;

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
