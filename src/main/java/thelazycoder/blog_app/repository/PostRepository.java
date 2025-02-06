package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import thelazycoder.blog_app.model.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query("SELECT p FROM Post p WHERE p.title =:title AND p.author.id = :authorId")
    Optional<Post> findByTitleAndAuthorId(String title, String authorId);
}
