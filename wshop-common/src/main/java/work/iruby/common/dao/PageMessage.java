package work.iruby.common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMessage<T> {
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private T data;

    public static <T> PageMessage<T> of(int pageNum, int pageSize, int totalPage, T data) {
        return new PageMessage<>(pageNum, pageSize, totalPage, data);
    }
}
