package work.iruby.wshop.common.dao;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsIdAndNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private int number;
}
