package thelazycoder.blog_app.exception.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import thelazycoder.blog_app.dto.response.ApiResponse;
import thelazycoder.blog_app.exception.DuplicateEntityException;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.exception.response.ErrorResponse;
import thelazycoder.blog_app.utils.ResponseUtil;


@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),e.getExceptions());
        System.out.println("Handling InvalidInputException: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse> handleDuplicateEntityException(DuplicateEntityException e) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                "Error",
                "Duplicate Entity",
                null
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<?> handleNoEntityFoundException(NoEntityFoundException e) {
        return new ResponseEntity<>(ResponseUtil.error(
                e.getMessage(), null
        ), HttpStatus.NOT_FOUND);
    }
}
