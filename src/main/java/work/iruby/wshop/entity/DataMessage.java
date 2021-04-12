package work.iruby.wshop.entity;

import lombok.Getter;

@Getter
public class DataMessage<T> {
    private final String message;
    private final T data;

    private DataMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> DataMessage<T> of(String message, T data) {
        return new DataMessage<T>(message, data);
    }

    public static <T> DataMessage<T> of(T data) {
        return new DataMessage<T>(null, data);
    }
}
