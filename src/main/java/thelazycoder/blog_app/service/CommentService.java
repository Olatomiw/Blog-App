package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.blog_app.dto.request.CommentRequest;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.mapper.ModelMapper;
import thelazycoder.blog_app.model.Comment;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.CommentRepository;
import thelazycoder.blog_app.repository.PostRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;
import thelazycoder.blog_app.utils.InfoGetter;
import thelazycoder.blog_app.utils.ResponseUtil;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final GenericFieldValidator genericFieldValidator;
    private final InfoGetter infoGetter;
    private final PostRepository postRepository;


    @Transactional
    public ResponseEntity<?> addComment(CommentRequest commentRequest, String postId) {
        User loggedInUser = infoGetter.getLoggedInUser();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoEntityFoundException("Post not found with id: " + postId));
        Comment comment = ModelMapper.mapToComment(commentRequest);
        comment.setAuthor(loggedInUser);
        comment.setPost(post);
        Comment validate = genericFieldValidator.validate(comment);
        Comment save = commentRepository.save(validate);
        return  new ResponseEntity<>(ResponseUtil.success(save, "Commented Successfully"), HttpStatus.OK);
    }

    @Transactional
    public void deleteComment(String id){
        commentRepository.deleteById(id);
    }

    @Transactional
    public ResponseEntity<?> updateComment(CommentRequest commentRequest, String postId) {
        User loggedInUser = infoGetter.getLoggedInUser();
        return null;
    }

}
