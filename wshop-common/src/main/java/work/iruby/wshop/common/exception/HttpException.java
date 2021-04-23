package work.iruby.wshop.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper = true)
public class HttpException extends RuntimeException {
    private int statusCode;

    public static HttpException forbidden(String message) {
        return new HttpException(HttpStatus.FORBIDDEN.value(), message);
    }

    public static HttpException notFound(String message) {
        return new HttpException(HttpStatus.NOT_FOUND.value(), message);
    }

    public static HttpException badRequest(String message) {
        return new HttpException(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static HttpException unAuthorized(String message) {
        return new HttpException(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static HttpException gone(String message) {
        return new HttpException(HttpStatus.GONE.value(), message);
    }

    private HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }


}
