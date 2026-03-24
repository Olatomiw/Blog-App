package thelazycoder.blog_app.dto.response;

import thelazycoder.blog_app.dto.request.CategoryDto;

import java.util.Set;

public record PreferenceResponseDto(String userId,
                                    Set<CategoryDto> preferredCategories) {
}
