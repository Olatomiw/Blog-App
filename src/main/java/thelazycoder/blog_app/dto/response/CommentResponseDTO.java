package thelazycoder.blog_app.dto.response;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        String id,
        String text,
        LocalDateTime createdAt,
        AuthorDTO authorDTO
) {
}
