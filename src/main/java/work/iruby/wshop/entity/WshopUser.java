package work.iruby.wshop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author iruby
 * @since 2021-04-07
 */
@Data
public class WshopUser {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    private String userName;

    private String userTel;

    private String userAvatarUrl;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Instant createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;


}
