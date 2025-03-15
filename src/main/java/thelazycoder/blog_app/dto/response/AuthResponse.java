package thelazycoder.blog_app.dto.response;

import thelazycoder.blog_app.model.User;

public record AuthResponse(
        String token, UserData userData
) {
}
