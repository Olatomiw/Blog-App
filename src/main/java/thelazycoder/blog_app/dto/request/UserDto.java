package thelazycoder.blog_app.dto.request;


public record UserDto(
        String firstName,
        String lastName,
        String username,
        String email,
        String bio,
        String password) {
}
