package thelazycoder.blog_app.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record PreferenceRequestDto(@NotNull Set<String> categoryIds ) {
}
