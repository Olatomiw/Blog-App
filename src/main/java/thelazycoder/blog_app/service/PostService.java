package thelazycoder.blog_app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.blog_app.config.CloudinaryService;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.exception.DuplicateEntityException;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.mapper.ModelMapper;
import thelazycoder.blog_app.model.Category;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.CategoryRepository;
import thelazycoder.blog_app.repository.PostRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;
import thelazycoder.blog_app.utils.InfoGetter;
import thelazycoder.blog_app.utils.ResponseUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final InfoGetter infoGetter;
    private final CloudinaryService cloudinaryService;
    private final GenericFieldValidator genericFieldValidator;
    @Lazy
    private final WebSocketService webSocketService;



    @CacheEvict(value = "AllPosts")
    @Transactional
    public PostResponse create(PostRequestDto postRequestDto, MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        User loggedInUser = infoGetter.getLoggedInUser();

        var imageUrl = cloudinaryService.uploadFile(file);

        Set<Category> categories =new HashSet<>(categoryRepository.findAllById(postRequestDto.categoryIds()));
        checkDuplicateTitle(postRequestDto.title(), loggedInUser.getId());


        Post post = createPost(postRequestDto, loggedInUser, imageUrl.get().get("secure_url").toString());
        Post validated = genericFieldValidator.validate(post);
        post.setCategories(categories);

        Post savePost = postRepository.save(validated);
        return ModelMapper.mapToPostResponse(savePost);
    }

    @Cacheable(value = "AllPosts")
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllPost(){
        List<Post> all = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (all.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No posts found");
        }
        List<PostResponse> postResponseList = all.stream().map(
                ModelMapper::mapToPostResponse
        ).toList();
        webSocketService.sendPostUpdate(postResponseList);
            return new ResponseEntity<>(ResponseUtil.success(postResponseList, "Successfully found all the posts"), HttpStatus.OK);
    }

    @Transactional
    public PostResponse getPostById(String id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoEntityFoundException("Entity with id " + id + " not found"));
        PostResponse postResponse = ModelMapper.mapToPostResponse(post);
        return postResponse;
    }

    @CacheEvict(value = "AllPosts")
    @Transactional
    public ResponseEntity<?> deletePostById(String id){
        postRepository.deleteById(id);
        return new ResponseEntity<>(ResponseUtil.success(null, "Successfully deleted post"), HttpStatus.OK);
    }



    public void checkDuplicateTitle(String title, String authorId){
        System.out.println("Checking for duplicate title: " + title + " for author: " + authorId);
        Optional<Post> byTitleAndAuthorId = postRepository.findByTitleAndAuthorId(title, authorId);
        if (byTitleAndAuthorId.isPresent()){
            System.out.println("Duplicate found!");
            throw new DuplicateEntityException("Change your post topic");
        }
    }
    public Post createPost(PostRequestDto postRequestDto, User loggedInUser, String imageUrl){
        Post post = ModelMapper.mapToPost(postRequestDto);
        post.setId(UUID.randomUUID().toString());
        post.setAuthor(loggedInUser);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setImageUrl(imageUrl);
        return post;
    }
}
