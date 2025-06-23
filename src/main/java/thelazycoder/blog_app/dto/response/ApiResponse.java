package thelazycoder.blog_app.dto.response;

public record ApiResponse<T>(
            String status,
            String message,
            T data
    ) {
}
