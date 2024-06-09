package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Assignment;
import com.devbridge.learning.Apptasks.models.Priority;
import com.devbridge.learning.Apptasks.models.Status;
import org.apache.ibatis.annotations.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface AssignmentRepository {
    @Select("SELECT a.*, c.category_id as categoryId, c.name as categoryName FROM assignments a " +
            "LEFT JOIN categories c ON a.category_id = c.category_id")
    @Results({
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category.categoryId", column = "categoryId"),
            @Result(property = "category.name", column = "categoryName"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "assignedToId", column = "assigned_to_id"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "priority", column = "priority", javaType = Priority.class),
            @Result(property = "createdDate", column = "created_date", javaType = OffsetDateTime.class),
            @Result(property = "assignedDate", column = "assigned_date", javaType = OffsetDateTime.class),
            @Result(property = "unassignedDate", column = "unassigned_date", javaType = OffsetDateTime.class),
            @Result(property = "doneDate", column = "done_date", javaType = OffsetDateTime.class)
    })
    List<Assignment> findAll();

    @Select("SELECT a.*, c.category_id as categoryId, c.name as categoryName FROM assignments a " +
            "LEFT JOIN categories c ON a.category_id = c.category_id " +
            "WHERE a.assignment_id = #{assignmentId}")
    @Results({
            @Result(property = "assignmentId", column = "assignment_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category.categoryId", column = "categoryId"),
            @Result(property = "category.name", column = "categoryName"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "assignedToId", column = "assigned_to_id"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "priority", column = "priority", javaType = Priority.class),
            @Result(property = "createdDate", column = "created_date", javaType = OffsetDateTime.class),
            @Result(property = "assignedDate", column = "assigned_date", javaType = OffsetDateTime.class),
            @Result(property = "unassignedDate", column = "unassigned_date", javaType = OffsetDateTime.class),
            @Result(property = "doneDate", column = "done_date", javaType = OffsetDateTime.class)
    })
    Optional<Assignment> findById(UUID assignmentId);

    @Insert("INSERT INTO assignments (assignment_id, title, category_id, description, created_by_id, " +
            "assigned_to_id, status, priority, created_date, assigned_date, unassigned_date, done_date) " +
            "VALUES (#{assignmentId}, #{title}, #{category.categoryId}, #{description}, #{createdById}, " +
            "#{assignedToId}, #{status}, #{priority}, #{createdDate}, #{assignedDate}, #{unassignedDate}, #{doneDate})")
    void create(Assignment assignment);

    @Update("UPDATE assignments SET title = #{title}, category_id = #{category.categoryId}, " +
            "description = #{description}, created_by_id = #{createdById}, assigned_to_id = #{assignedToId}, " +
            "status = #{status}, priority = #{priority}, created_date = #{createdDate}, " +
            "assigned_date = #{assignedDate}, unassigned_date = #{unassignedDate}, done_date = #{doneDate} " +
            "WHERE assignment_id = #{assignmentId}")
    void update(Assignment assignment);

    @Delete("DELETE FROM assignments WHERE assignment_id = #{assignmentId}")
    void delete(UUID assignmentId);
}