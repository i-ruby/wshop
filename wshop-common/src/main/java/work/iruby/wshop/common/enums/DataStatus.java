package work.iruby.wshop.common.enums;

public enum DataStatus {
    OK("ok"),
    DELETED("deleted");
    private final String value;

    DataStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value();
    }
}
