package thelazycoder.blog_app.dto.response;

import thelazycoder.blog_app.model.Role;

import java.time.LocalDateTime;
import java.util.List;

public record UserData(
        String id,
        String firstName,
        String lastName,
        String email,
        String username,
        String profilePicture,
        LocalDateTime createdAt,
        Role role,
        List<PostResponse> postResponseList
) {

}
