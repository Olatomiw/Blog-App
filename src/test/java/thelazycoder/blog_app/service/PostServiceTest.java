//package thelazycoder.blog_app.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import thelazycoder.blog_app.dto.request.PostRequestDto;
//import thelazycoder.blog_app.dto.response.AuthorDTO;
//import thelazycoder.blog_app.dto.response.PostResponse;
//import thelazycoder.blog_app.exception.DuplicateEntityException;
//import thelazycoder.blog_app.model.Post;
//import thelazycoder.blog_app.model.User;
//import thelazycoder.blog_app.repository.PostRepository;
//import thelazycoder.blog_app.repository.UserRepository;
//import thelazycoder.blog_app.utils.GenericFieldValidator;
//import thelazycoder.blog_app.utils.InfoGetter;
//import thelazycoder.blog_app.utils.ResponseUtil;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PostServiceTest {
//
//    @Mock
//    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private PostService postService;
//    @Mock
//    private InfoGetter infoGetter;
//    @Mock
//    private GenericFieldValidator genericFieldValidator;
//
//
//    private User mockUser;
//    private Post mockPost;
//    private PostRequestDto mockRequestDto;
//    private PostResponse mockPostResponse;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        mockRequestDto = new PostRequestDto(
//                "The Chosen",
//                "This is a content",
//                "Draft"
//        );
//
//        mockUser = new User();
//        mockUser.setId(UUID.randomUUID().toString());
//        mockUser.setFirstName("John");
//        mockUser.setLastName("Doe");
//        mockUser.setUsername("username");
//        mockUser.setEmail("johndoe@gmail.com");
//
//        mockPost = new Post();
//        mockPost.setId(UUID.randomUUID().toString());
//        mockPost.setCreatedAt(LocalDateTime.now());
//        mockPost.setUpdatedAt(LocalDateTime.now());
//        mockPost.setStatus("SUC");
//        mockPost.setContent(mockRequestDto.content());
//        mockPost.setTitle(mockRequestDto.title());
//        mockPost.setAuthor(mockUser);
//
////        response
//        mockPostResponse = new PostResponse(
//                mockPost.getId(),
//                mockPost.getTitle(),
//                mockPost.getContent(),
//                mockPost.getStatus(),
//                mockPost.getCreatedAt(),
//                mockPost.getUpdatedAt(),
//                new AuthorDTO(
//                        mockPost.getAuthor().getId(), mockPost.getAuthor().getUsername()
//                ),null,null
//        );
//    }
//    @Test
//    void create() {
//        when(infoGetter.getLoggedInUser()).thenReturn(mockUser);
//        when(genericFieldValidator.validate(any(Post.class))).thenReturn(mockPost);
//        when(postRepository.save(any(Post.class))).thenReturn(mockPost);
//
//        ResponseEntity<?> response = postService.create(mockRequestDto);
//
//        assertNotNull(response);
//        assertNotNull(response.getBody());
//        verify(postRepository, times(1)).save(any(Post.class));
//    }
//
//    @Test
//    void findAllPost() {
//        when(postRepository.findAll()).thenReturn(List.of(mockPost));
//
//        ResponseEntity<?> allPost = postService.findAllPost();
//
//        assertNotNull(allPost);
//        assertNotNull(allPost.getBody());
//        verify(postRepository, times(1)).findAll();
//
//    }
//
//    @Test
//    void getPostById() {
//        when(postRepository.findById(mockPost.getId())).thenReturn(Optional.ofNullable(mockPost));
//
//        ResponseEntity<?> postById = postService.getPostById(mockPost.getId());
//
//        assertNotNull(postById);
//        assertEquals(ResponseUtil.success(mockPostResponse, "Successfully found post"),postById.getBody());
//        verify(postRepository, times(1)).findById(mockPost.getId());
//    }
//
//    @Test
//    void shouldThrowExceptionForDuplicateTitle(){
//        when(infoGetter.getLoggedInUser()).thenReturn(mockUser);
//        when(postRepository.findByTitleAndAuthorId("The Chosen",
//                mockUser.getId())).thenReturn(Optional.of(mockPost));
//
//        DuplicateEntityException duplicateEntityException = assertThrows(DuplicateEntityException.class,
//                () -> postService.create(mockRequestDto));
//        assertEquals("Change your post topic" , duplicateEntityException.getMessage());
//        verify(postRepository, times(1)).findByTitleAndAuthorId(mockRequestDto.title(), mockUser.getId());
//    }
//
//    @Test
//    void deletePostById() {
//
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        mockRequestDto = null;
//    }
//}