package thelazycoder.blog_app.exception;

import lombok.Data;
import lombok.Getter;
import thelazycoder.blog_app.exception.response.ExceptionResponse;

import java.util.List;

@Data
public class InvalidInputException extends RuntimeException {

    List<ExceptionResponse> exceptions ;


    public InvalidInputException(String message, List<ExceptionResponse> exceptions) {
        super(message);
        this.exceptions = exceptions;
    }
}
