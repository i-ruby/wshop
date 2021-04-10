package work.iruby.wshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private Boolean login;
    private WshopUser user;
}
