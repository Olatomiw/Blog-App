package thelazycoder.blog_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import thelazycoder.blog_app.dto.request.CategoryDto;
import thelazycoder.blog_app.dto.response.CategoryDTO;
import thelazycoder.blog_app.exception.DuplicateEntityException;
import thelazycoder.blog_app.model.Category;
import thelazycoder.blog_app.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<?> findAll() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDTO> list = all.stream().map(
                category -> new CategoryDTO(category.getId(), category.getName())
        ).toList();
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<?> create(String name) {
        if(categoryRepository.existsByName(name)){
            throw new DuplicateEntityException("Category already exists");
        }
        Category category= new Category();
        category.setName(name);
        categoryRepository.save(category);
        return ResponseEntity.ok().body(category);
    }
}
