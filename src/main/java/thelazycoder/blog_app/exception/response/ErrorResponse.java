package thelazycoder.blog_app.exception.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private String message;
    private List<ExceptionResponse> errors;

    public ErrorResponse(String message, List<ExceptionResponse> errors) {
        this.message = message;
        this.errors = errors;
    }
}
