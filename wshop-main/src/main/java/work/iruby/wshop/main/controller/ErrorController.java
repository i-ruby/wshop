package work.iruby.wshop.main.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.exception.HttpException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(HttpException.class)
    private DataMessage<String> onError(HttpServletResponse response, HttpException httpException) {
        response.setStatus(httpException.getStatusCode());
        return DataMessage.of(httpException.getMessage());
    }
}
