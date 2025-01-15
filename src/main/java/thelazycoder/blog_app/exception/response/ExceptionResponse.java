package thelazycoder.blog_app.exception.response;

import lombok.*;

@Data
public class ExceptionResponse {

    private String message;
    private String fieldName;

    public ExceptionResponse(String message, String fieldName) {
        this.message = message;
        this.fieldName = fieldName;
    }
}
