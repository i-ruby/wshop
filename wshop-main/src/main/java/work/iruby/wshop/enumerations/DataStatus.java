package work.iruby.wshop.enumerations;

public enum DataStatus {
    OK("ok"),
    DELETED("deleted");
    private String value;

    DataStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
