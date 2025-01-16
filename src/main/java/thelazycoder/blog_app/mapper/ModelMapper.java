package thelazycoder.blog_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;

@RequiredArgsConstructor
public class  ModelMapper {


    public static User mapDtoToModel(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setBio(dto.bio());
        return user;
    }

    public static Post mapToPost(PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.title());
        post.setStatus(dto.status());
        post.setContent(dto.content());

        return post;
    }

    public static PostResponse mapToPostResponse(Post post) {
        PostResponse postResponse = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus()
        );
        return postResponse;
    }


}
