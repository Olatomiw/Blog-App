package thelazycoder.blog_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelazycoder.blog_app.model.Category;
import thelazycoder.blog_app.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query("SELECT p FROM Post p WHERE p.title =:title AND p.author.id = :authorId")
    Optional<Post> findByTitleAndAuthorId(String title, String authorId);
    Page<Post> findAll(Pageable pageable);

    @Query("""
    SELECT DISTINCT p FROM Post p
    JOIN p.categories c
    WHERE c IN :categories
    ORDER BY p.createdAt DESC
""")
    Page<Post> findByCategoriesIn(
            @Param("categories") Set<Category> categories,
            Pageable pageable
    );
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
