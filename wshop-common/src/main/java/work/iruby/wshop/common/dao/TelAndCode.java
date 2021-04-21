package work.iruby.wshop.common.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class TelAndCode implements Serializable {
    private static final long serialVersionUID = 1L;
    String tel;
    String code;
}