package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
