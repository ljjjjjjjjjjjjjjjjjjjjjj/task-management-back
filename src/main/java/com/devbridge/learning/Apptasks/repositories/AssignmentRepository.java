package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface AssignmentRepository {
    @Select("SELECT * FROM assignments")
    @Results({
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category", column = "category"),
            @Result(property = "description", column = "description"),
            @Result(property = "employeeId", column = "employee_Id")
    })
    List<Assignment> findAll();

    @Select("SELECT * FROM assignments WHERE assignment_id = #{assignmentId}")
    @Results({
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category", column = "category"),
            @Result(property = "description", column = "description"),
            @Result(property = "employeeId", column = "employee_Id")
    })
    Optional<Assignment> findById(UUID assignmentId);

    @Insert("INSERT INTO assignments (assignment_id, title, category, description, employee_id) VALUES (#{assignmentId}, #{title}, #{category}, #{description}, #{employeeId})")
    void create(Assignment assignment);

    @Update("UPDATE assignments SET title = #{title}, category = #{category}, description = #{description}, employee_id = #{employeeId} WHERE assignment_id = #{assignmentId}")
    void update(Assignment assignment);

    @Delete("DELETE FROM assignments WHERE assignment_id = #{assignmentId}")
    void delete(UUID assignmentId);
}