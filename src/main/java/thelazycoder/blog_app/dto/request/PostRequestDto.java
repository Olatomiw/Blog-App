package thelazycoder.blog_app.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PostRequestDto(@NotNull String title, @NotNull String content, @NotNull String status, List<String>categoryIds) {
}
