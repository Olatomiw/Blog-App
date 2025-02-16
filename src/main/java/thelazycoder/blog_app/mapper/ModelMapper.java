package thelazycoder.blog_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import thelazycoder.blog_app.dto.request.CommentRequest;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.dto.response.CommentResponse;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.model.Comment;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;

import java.time.LocalDateTime;

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

    public static Comment mapToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.text());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    public static CommentResponse mapCommentResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getText(),
                comment.getPost().getId()
        );
        return commentResponse;
    }


}
