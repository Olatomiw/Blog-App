package thelazycoder.blog_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import thelazycoder.blog_app.dto.request.CommentRequest;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.dto.response.*;
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
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                new AuthorDTO(post.getAuthor().getId(), post.getAuthor().getUsername()),
                post.getCategories().stream().map(
                        category -> new CategoryDTO(category.getId(), category.getName())
                ).toList(),
                post.getComments().stream().map(
                        comment -> new CommentResponseDTO(
                                comment.getId(),
                                comment.getText(),
                                comment.getCreatedAt(),
                                new AuthorDTO(comment.getAuthor().getId(), comment.getAuthor().getUsername())
                        )
                ).toList()
        );
    }

    public static Comment mapToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.text());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    public static CommentResponse mapCommentResponse(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getText(),
                comment.getPost().getId()
        );
    }


}
