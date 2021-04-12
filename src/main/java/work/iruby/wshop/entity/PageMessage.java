package work.iruby.wshop.entity;

import lombok.Getter;

@Getter
public class PageMessage<T> {
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private final T data;

    private PageMessage(int pageNum, int pageSize, int totalPage, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.data = data;
    }

    public static <T> PageMessage<T> of(int pageNum, int pageSize, int totalPage, T data) {
        return new PageMessage<T>(pageNum, pageSize, totalPage, data);
    }
}
