package thelazycoder.blog_app.exception.ExceptionHandler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.exception.response.ErrorResponse;


@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),e.getExceptions());
        System.out.println("Handling InvalidInputException: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
