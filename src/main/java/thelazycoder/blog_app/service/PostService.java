package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.mapper.ModelMapper;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.PostRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;
import thelazycoder.blog_app.utils.InfoGetter;
import thelazycoder.blog_app.utils.ResponseUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final InfoGetter infoGetter;
    private final GenericFieldValidator genericFieldValidator;

    @Transactional
    public ResponseEntity<?> create(PostRequestDto postRequestDto){
        User loggedInUser = infoGetter.getLoggedInUser();
        Post post = ModelMapper.mapToPost(postRequestDto);
        post.setId(UUID.randomUUID().toString());
        post.setAuthor(loggedInUser);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        Post validated = genericFieldValidator.validate(post);
        Post savePost = postRepository.save(validated);
        PostResponse postResponse = ModelMapper.mapToPostResponse(savePost);
            return new ResponseEntity<>(ResponseUtil.success(postResponse, "Successfully created"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> findAllPost(){
        List<Post> all = postRepository.findAll();
        if (all.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No posts found");
        }
        List<PostResponse> postResponseList = all.stream().map(
                ModelMapper::mapToPostResponse
        ).toList();
            return new ResponseEntity<>(ResponseUtil.success(postResponseList, "Successfully found all the posts"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getPostById(String id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoEntityFoundException("Entity with id " + id + " not found"));
        PostResponse postResponse = ModelMapper.mapToPostResponse(post);
        return new ResponseEntity<>(ResponseUtil.success(postResponse, "Successfully found post"),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deletePostById(String id){
        postRepository.deleteById(id);
        return new ResponseEntity<>(ResponseUtil.success(null, "Successfully deleted post"), HttpStatus.OK);
    }

}
