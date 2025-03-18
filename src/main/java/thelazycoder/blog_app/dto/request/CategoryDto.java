package thelazycoder.blog_app.dto.request;

import jakarta.validation.constraints.NotNull;

public record CategoryDto(@NotNull String name) {
}
