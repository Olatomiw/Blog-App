package thelazycoder.blog_app.dto.response;

import java.time.LocalDateTime;

public record UserData(
        String id,
        String firstName,
        String lastName,
        String email,
        String username,
        String profilePicture,
        LocalDateTime createdAt,
        thelazycoder.blog_app.model.Role role
) {

}
