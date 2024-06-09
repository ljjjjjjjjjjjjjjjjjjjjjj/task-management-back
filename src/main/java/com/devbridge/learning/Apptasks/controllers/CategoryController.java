package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/id/{categoryId}")
    public Category getCategoryById(@PathVariable UUID categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/name/{name}")
    public Category getCategoryById(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable UUID categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}