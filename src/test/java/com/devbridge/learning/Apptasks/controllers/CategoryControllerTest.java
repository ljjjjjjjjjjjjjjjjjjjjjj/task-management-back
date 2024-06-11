package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.models.Category;
import com.devbridge.learning.Apptasks.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {
private MockMvc mockMvc;

@Mock
private CategoryService categoryService;

@InjectMocks
private CategoryController categoryController;

@BeforeEach
public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
}

@Test
public void testGetAllCategories() throws Exception {
    Category category = new Category(UUID.randomUUID(), "Category1");
    when(categoryService.getAllCategories()).thenReturn(List.of(category));

    mockMvc.perform(get("/categories")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"categoryId\":\"" + category.getCategoryId() + "\",\"name\":\"Category1\"}]"));
}

@Test
public void testGetCategoryById() throws Exception {
    UUID categoryId = UUID.randomUUID();
    Category category = new Category(categoryId, "Category1");
    when(categoryService.getCategoryById(eq(categoryId))).thenReturn(category);

    mockMvc.perform(get("/categories/id/{categoryId}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"categoryId\":\"" + categoryId + "\",\"name\":\"Category1\"}"));
}

@Test
public void testGetCategoryByName() throws Exception {
    String categoryName = "Category1";
    Category category = new Category(UUID.randomUUID(), categoryName);
    when(categoryService.getCategoryByName(eq(categoryName))).thenReturn(category);

    mockMvc.perform(get("/categories/name/{name}", categoryName)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"categoryId\":\"" + category.getCategoryId() + "\",\"name\":\"Category1\"}"));
}

@Test
public void testCreateCategory() throws Exception {
    Category category = new Category(UUID.randomUUID(), "Category1");
    when(categoryService.createCategory(any(Category.class))).thenReturn(category);

    mockMvc.perform(post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(category)))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"categoryId\":\"" + category.getCategoryId() + "\",\"name\":\"Category1\"}"));
}

@Test
public void testUpdateCategory() throws Exception {
    UUID categoryId = UUID.randomUUID();
    Category category = new Category(categoryId, "UpdatedCategory");
    when(categoryService.updateCategory(eq(categoryId), any(Category.class))).thenReturn(category);

    mockMvc.perform(put("/categories/{categoryId}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(category)))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"categoryId\":\"" + categoryId + "\",\"name\":\"UpdatedCategory\"}"));
}

@Test
public void testDeleteCategory() throws Exception {
    UUID categoryId = UUID.randomUUID();
    mockMvc.perform(delete("/categories/{categoryId}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
}
}