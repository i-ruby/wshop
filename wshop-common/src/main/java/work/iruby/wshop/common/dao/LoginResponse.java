package work.iruby.wshop.common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.iruby.wshop.common.entity.User;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean login;
    private User user;
}
