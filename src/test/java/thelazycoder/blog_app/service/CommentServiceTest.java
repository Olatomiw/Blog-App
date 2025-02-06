package thelazycoder.blog_app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import thelazycoder.blog_app.dto.request.CommentRequest;
import thelazycoder.blog_app.model.Comment;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.CommentRepository;
import thelazycoder.blog_app.repository.PostRepository;
import thelazycoder.blog_app.repository.UserRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;
import thelazycoder.blog_app.utils.InfoGetter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private InfoGetter infoGetter;
    @Mock
    private GenericFieldValidator genericFieldValidator;

    private User mockUser;
    private Post mockPost;
    private Comment mockComment;
    private CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(UUID.randomUUID().toString());
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setUsername("username");
        mockUser.setEmail("johndoe@gmail.com");
        commentRequest = new CommentRequest(
                "This is a comment");

        mockPost = new Post();
        mockPost.setId(UUID.randomUUID().toString());
        mockPost.setCreatedAt(LocalDateTime.now());
        mockPost.setUpdatedAt(LocalDateTime.now());
        mockPost.setStatus("SUC");
        mockPost.setContent("This is a comment");
        mockPost.setTitle("This is a title");
        mockPost.setAuthor(mockUser);

        mockComment = new Comment();
        mockComment.setId(UUID.randomUUID().toString());
        mockComment.setAuthor(mockUser);
        mockComment.setPost(mockPost);
        mockComment.setText(commentRequest.text());
        mockComment.setCreatedAt(LocalDateTime.now());

    }

    @Test
    void addComment() {
        when(infoGetter.getLoggedInUser()).thenReturn(mockUser);
        when(postRepository.findById(mockPost.getId())).thenReturn(Optional.of(mockPost));
        when(genericFieldValidator.validate(any(Comment.class))).thenReturn(mockComment);
        when(commentRepository.save(mockComment)).thenReturn(mockComment);

        ResponseEntity<?> response = commentService.addComment(commentRequest, mockPost.getId());

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteComment() {
    }

    @Test
    void updateComment() {
    }
}