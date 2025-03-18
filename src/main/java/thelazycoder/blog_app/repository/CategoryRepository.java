package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
}
