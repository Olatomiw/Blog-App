package thelazycoder.blog_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.request.CommentRequest;
import thelazycoder.blog_app.service.CommentService;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createComment(@Valid @PathVariable String id, @RequestBody CommentRequest commentRequest) {
       return commentService.addComment(commentRequest, id);
    }

    @DeleteMapping("post/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String postId,
                                           @PathVariable String commentId) {
        return commentService.deleteComment(postId,commentId);
    }
}
