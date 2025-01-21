package thelazycoder.blog_app.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentRequest(@NotNull String text) {
}
