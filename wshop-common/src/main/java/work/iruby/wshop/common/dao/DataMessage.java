package work.iruby.wshop.common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataMessage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private T data;

    public static <T> DataMessage<T> of(String message, T data) {
        return new DataMessage<>(message, data);
    }

    public static <T> DataMessage<T> of(T data) {
        return new DataMessage<>(null, data);
    }

}
