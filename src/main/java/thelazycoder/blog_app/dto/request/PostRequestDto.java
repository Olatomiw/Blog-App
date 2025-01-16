package thelazycoder.blog_app.dto.request;

import jakarta.validation.constraints.NotNull;

public record PostRequestDto(@NotNull String title,@NotNull String content,@NotNull String status) {
}
