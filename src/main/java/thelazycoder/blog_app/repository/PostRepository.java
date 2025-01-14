package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.Post;

public interface PostRepository extends JpaRepository<Post, String> {
}
