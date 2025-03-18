package thelazycoder.blog_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.request.CategoryDto;
import thelazycoder.blog_app.exception.DuplicateEntityException;
import thelazycoder.blog_app.repository.CategoryRepository;
import thelazycoder.blog_app.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestParam String name) {
       return categoryService.create(name);
    }
}
