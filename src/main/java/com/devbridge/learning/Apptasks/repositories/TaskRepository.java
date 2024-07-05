package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Task;
import com.devbridge.learning.Apptasks.models.Priority;
import com.devbridge.learning.Apptasks.models.Status;
import org.apache.ibatis.annotations.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TaskRepository {
    @Select("SELECT t.*, c.category_id as categoryId, c.name as categoryName FROM tasks t " +
            "LEFT JOIN categories c ON t.category_id = c.category_id")
    @Results({
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category.categoryId", column = "categoryId"),
            @Result(property = "category.name", column = "categoryName"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "assignedToId", column = "assigned_to_id"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "priority", column = "priority", javaType = Priority.class),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "createdDate", column = "created_date", javaType = OffsetDateTime.class),
            @Result(property = "assignedDate", column = "assigned_date", javaType = OffsetDateTime.class),
            @Result(property = "unassignedDate", column = "unassigned_date", javaType = OffsetDateTime.class),
            @Result(property = "doneDate", column = "done_date", javaType = OffsetDateTime.class)
    })
    List<Task> findAll();

    @Select("SELECT t.*, c.category_id as categoryId, c.name as categoryName FROM tasks t " +
            "LEFT JOIN categories c ON t.category_id = c.category_id " +
            "WHERE t.task_id = #{taskId}")
    @Results({
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category.categoryId", column = "categoryId"),
            @Result(property = "category.name", column = "categoryName"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "assignedToId", column = "assigned_to_id"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "priority", column = "priority", javaType = Priority.class),
            @Result(property = "createdDate", column = "created_date", javaType = OffsetDateTime.class),
            @Result(property = "assignedDate", column = "assigned_date", javaType = OffsetDateTime.class),
            @Result(property = "unassignedDate", column = "unassigned_date", javaType = OffsetDateTime.class),
            @Result(property = "doneDate", column = "done_date", javaType = OffsetDateTime.class)
    })
    Optional<Task> findByTaskId(UUID taskId);

    @Select("SELECT t.*, c.category_id as categoryId, c.name as categoryName FROM tasks t " +
            "LEFT JOIN categories c ON t.category_id = c.category_id " +
            "WHERE project_id = #{projectId}")
    @Results({
            @Result(property = "taskId", column = "task_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "category.categoryId", column = "categoryId"),
            @Result(property = "category.name", column = "categoryName"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "assignedToId", column = "assigned_to_id"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "priority", column = "priority", javaType = Priority.class),
            @Result(property = "createdDate", column = "created_date", javaType = OffsetDateTime.class),
            @Result(property = "assignedDate", column = "assigned_date", javaType = OffsetDateTime.class),
            @Result(property = "unassignedDate", column = "unassigned_date", javaType = OffsetDateTime.class),
            @Result(property = "doneDate", column = "done_date", javaType = OffsetDateTime.class)
    })
    List<Task> findTasksByProjectId(UUID projectId);

    @Insert("INSERT INTO tasks (task_id, title, category_id, description, created_by_id, " +
            "assigned_to_id, status, priority, project_id, created_date, assigned_date, unassigned_date, done_date) " +
            "VALUES (#{taskId}, #{title}, #{category.categoryId}, #{description}, #{createdById}, " +
            "#{assignedToId}, #{status}, #{priority}, #{projectId}, #{createdDate}, #{assignedDate}, " +
            "#{unassignedDate}, #{doneDate})")
    void create(Task task);

    @Update("UPDATE tasks SET title = #{title}, category_id = #{category.categoryId}, " +
            "description = #{description}, created_by_id = #{createdById}, assigned_to_id = #{assignedToId}, " +
            "status = #{status}, priority = #{priority}, project_id = #{projectId}, created_date = #{createdDate}, " +
            "assigned_date = #{assignedDate}, unassigned_date = #{unassignedDate}, done_date = #{doneDate} " +
            "WHERE task_id = #{taskId}")
    void update(Task task);

    @Update("UPDATE tasks SET project_id = #{projectId} WHERE task_id = #{taskId}")
    void updateProjectId(Task task);

    @Delete("DELETE FROM tasks WHERE task_id = #{taskId}")
    void delete(UUID taskId);
}