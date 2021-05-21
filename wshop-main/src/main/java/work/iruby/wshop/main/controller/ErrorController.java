package work.iruby.wshop.main.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.exception.HttpException;

import javax.servlet.http.HttpServletResponse;

/**
 * 捕获所有的自定义HttpException,统一包装错误码和错误message
 */
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(HttpException.class)
    public DataMessage<String> onError(HttpServletResponse response, HttpException httpException) {
        response.setStatus(httpException.getStatusCode());
        return DataMessage.of(httpException.getMessage());
    }
}
