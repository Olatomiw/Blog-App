package thelazycoder.blog_app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse (
        String id,
        String title,
        String content,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        AuthorDTO author,
        List<CategoryDTO> categories,
        List<CommentResponseDTO> comments
)
{
}
