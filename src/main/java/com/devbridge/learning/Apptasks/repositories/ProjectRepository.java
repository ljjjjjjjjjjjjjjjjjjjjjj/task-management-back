package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProjectRepository {

    @Select("SELECT * FROM projects")
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "progress", column = "progress")
    })
    List<Project> findAll();

    @Select("SELECT * FROM projects WHERE project_id = #{projectId}")
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "progress", column = "progress")
    })
    Optional<Project> findById(UUID projectId);

    @Insert("INSERT INTO projects (project_id, project_name, team_id, created_date, start_date, initial_deadline_date, end_date, status, progress) " +
            "VALUES (#{projectId}, #{projectName}, #{teamId}, #{createdDate}, #{startDate}, #{initialDeadlineDate}, #{endDate}, #{status}, #{progress})")
    void create(Project project);

    @Update("UPDATE projects SET project_name = #{projectName}, team_id = #{teamId}, created_date = #{createdDate}, start_date = #{startDate}, " +
            "initial_deadline_date = #{initialDeadlineDate}, end_date = #{endDate}, status = #{status}, progress = #{progress} WHERE project_id = #{projectId}")
    void update(Project project);

    @Delete("DELETE FROM projects WHERE project_id = #{projectId}")
    void delete(UUID projectId);
}
