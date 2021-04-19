package work.iruby.wshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataMessage<T> {
    private String message;
    private T data;

    public static <T> DataMessage<T> of(String message, T data) {
        return new DataMessage<T>(message, data);
    }

    public static <T> DataMessage<T> of(T data) {
        return new DataMessage<T>(null, data);
    }

}
