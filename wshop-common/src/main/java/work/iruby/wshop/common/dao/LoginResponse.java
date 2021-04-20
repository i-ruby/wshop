package work.iruby.wshop.common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.iruby.wshop.common.entity.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Boolean login;
    private User user;
}
