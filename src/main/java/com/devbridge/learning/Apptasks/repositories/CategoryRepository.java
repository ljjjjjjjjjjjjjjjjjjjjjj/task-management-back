package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CategoryRepository {
    @Select("SELECT * FROM categories")
    @Results({
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name")
    })
    List<Category> findAll();

    @Select("SELECT * FROM categories WHERE category_id = #{categoryId}")
    @Results({
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name")
    })
    Optional<Category> findById(UUID categoryId);

    @Select("SELECT * FROM categories WHERE name = #{name}")
    @Results({
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name")
    })
    Optional<Category> findByName(String name);

    @Insert("INSERT INTO categories (category_id, name) VALUES (#{categoryId}, #{name})")
    void create(Category category);

    @Update("UPDATE categories SET name = #{name} WHERE category_id = #{categoryId}")
    void update(Category category);

    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    void delete(UUID categoryId);

}
