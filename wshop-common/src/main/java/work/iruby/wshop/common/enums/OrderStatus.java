package work.iruby.wshop.common.enums;

public enum OrderStatus {
    PENDING("pending"),
    PAID("paid"),
    DELIVERED("delivered"),
    RECEIVED("received");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public OrderStatus getEnum(String name) {
        return valueOf(name.toLowerCase());
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value();
    }
}
