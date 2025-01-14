package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
