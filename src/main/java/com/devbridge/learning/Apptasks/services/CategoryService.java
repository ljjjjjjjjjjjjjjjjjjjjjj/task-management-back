package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final static String CATEGORY_NOT_FOUND = "Category with given id not found";
    private final static String CATEGORY_ALREADY_EXISTS = "Category with this name already exists";
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }

    public Category createCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }

        category.setCategoryId(UUID.randomUUID());
        categoryRepository.create(category);

        return category;
    }

    public Category updateCategory(UUID categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));

        Optional<Category> categoryWithSameName = categoryRepository.findByName(updatedCategory.getName());
        if (categoryWithSameName.isPresent()) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }

        existingCategory.setName(updatedCategory.getName());
        categoryRepository.update(existingCategory);
        return existingCategory;
    }

    public void deleteCategory(UUID categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));

        categoryRepository.delete(categoryId);
    }
}