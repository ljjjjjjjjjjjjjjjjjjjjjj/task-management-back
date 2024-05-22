package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AssignmentRepository {
    @Select("SELECT * FROM tasks.assignments")
    List<Assignment> findAll();

    @Select("SELECT * FROM tasks.assignments WHERE assignment_id = #{assignmentId}")
    Assignment findById(UUID assignmentId);

    @Insert("INSERT INTO tasks.assignments (assignment_id, title, employee_id) VALUES (#{assignmentId}, #{title}, #{employeeId})")
    void save(Assignment assignment);

    @Update("UPDATE tasks.assignments SET title = #{title}, employee_id = #{employeeId} WHERE assignment_id = #{assignmentId}")
    void update(Assignment assignment);

    @Delete("DELETE FROM tasks.assignments WHERE assignment_id = #{assignmentId}")
    void delete(UUID assignmentId);
}