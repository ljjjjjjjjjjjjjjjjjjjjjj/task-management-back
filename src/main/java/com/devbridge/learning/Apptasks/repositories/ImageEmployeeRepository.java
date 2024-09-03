package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.ImageEmployee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Mapper
public interface ImageEmployeeRepository {
    @Insert("INSERT INTO employee_images (image_id, image_title, image_data, employee_id) " +
            "VALUES (#{imageId}, #{imageTitle}, #{imageData}, #{employeeId})")
    void create(ImageEmployee imageEmployeemage);

    @Select("SELECT * FROM employee_images WHERE employee_id = #{employeeId}")
    @Results({
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "imageTitle", column = "image_title"),
            @Result(property = "imageData", column = "image_data"),
            @Result(property = "employeeId", column = "employee_id")
    })
    Optional<ImageEmployee> findByEmployeeId(UUID employeeId);

    @Select("SELECT * FROM employee_images WHERE image_id = #{imageId}")
    @Results({
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "imageTitle", column = "image_title"),
            @Result(property = "imageData", column = "image_data"),
            @Result(property = "employeeId", column = "employee_id")
    })
    Optional<ImageEmployee> findByImageId(UUID employeeId);

    @Select("SELECT * FROM employee_images")
    @Results({
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "imageTitle", column = "image_title"),
            @Result(property = "imageData", column = "image_data"),
            @Result(property = "employeeId", column = "employee_id")
    })
    List<ImageEmployee> findAll();

    @Select({
            "<script>",
            "SELECT * FROM employee_images WHERE image_id IN",
            "<foreach item='imageId' collection='imageIds' open='(' separator=',' close=')'>",
            "#{imageId}",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "imageTitle", column = "image_title"),
            @Result(property = "imageData", column = "image_data"),
            @Result(property = "employeeId", column = "employee_id")
    })
    Set<ImageEmployee> findByImageIds(@Param("imageIds") Set<UUID> imageIds);

    @Delete("DELETE FROM employee_images WHERE image_id = #{imageId}")
    void deleteByImageId(UUID employeeId);
}
