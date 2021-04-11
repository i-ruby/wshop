package work.iruby.wshop.entity;

public class PageMessage<T> {
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private final T data;

    public PageMessage(int pageNum, int pageSize, int totalPage, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.data = data;
    }
}
