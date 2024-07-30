package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.configuration.mybatis.UuidSetTypeHandler;
import com.devbridge.learning.Apptasks.models.Project;
import com.devbridge.learning.Apptasks.models.Status;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Mapper
public interface ProjectRepository {

    @Select("SELECT * FROM projects")
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    List<Project> findAll();

    @Select("SELECT * FROM projects WHERE project_id = #{projectId}")
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    Optional<Project> findById(UUID projectId);

    @Select("SELECT * FROM projects WHERE created_by_id = #{employeeId}")
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    List<Project> findByCreatedById(UUID employeeId);

    @Select({
            "<script>",
            "SELECT p.* FROM projects p",
            "JOIN project_participants pp ON p.project_id = pp.project_id",
            "WHERE pp.employee_id = #{employeeId}",
            "</script>"
    })
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    List<Project> findByEmployeeId(UUID employeeId);

    @Select({
            "<script>",
            "SELECT p.* FROM projects p",
            "JOIN project_participants pp ON p.project_id = pp.project_id",
            "WHERE pp.employee_id = #{employeeId}",
            "AND p.status = #{status}",
            "</script>"
    })
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    List<Project> findByEmployeeIdAndStatus(UUID employeeId, String status);

    @Select({
            "<script>",
            "SELECT p.* FROM projects p",
            "JOIN project_teams pt ON p.project_id = pt.project_id",
            "WHERE pt.team_id IN",
            "<foreach item='teamId' collection='teamIds' open='(' separator=',' close=')'>",
            "#{teamId}",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "teamIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.TeamRepository.findTeamIdsByProjectId")),
            @Result(property = "createdById", column = "created_by_id"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "initialDeadlineDate", column = "initial_deadline_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "status", column = "status", javaType = Status.class),
            @Result(property = "progress", column = "progress"),
            @Result(property = "participantIds", column = "project_id",
                    javaType = Set.class,
                    typeHandler = UuidSetTypeHandler.class,
                    many = @Many(select =
                            "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeIdsByProjectId")),
    })
    List<Project> findByTeamIds(@Param("teamIds") Set<UUID> teamIds);

    @Select("SELECT project_name FROM projects WHERE project_id = #{projectId}")
    String findProjectNameById(UUID projectId);

    @Select("SELECT COUNT(*) > 0 FROM project_participants WHERE project_id = #{projectId} AND employee_id = #{employeeId}")
    boolean existsProjectParticipant(@Param("projectId") UUID projectId, @Param("employeeId") UUID employeeId);

    @Insert("INSERT INTO projects (project_id, project_name, created_by_id, " +
            "created_date, start_date, initial_deadline_date, end_date, status, progress) " +
            "VALUES (#{projectId}, #{projectName}, #{createdById}, " +
            "#{createdDate}, #{startDate}, #{initialDeadlineDate}, #{endDate}, #{status}, #{progress})")
    void create(Project project);

    @Update("UPDATE projects SET project_name = #{projectName}, created_by_id = #{createdById}, " +
            "created_date = #{createdDate}, start_date = #{startDate}, initial_deadline_date = #{initialDeadlineDate}, " +
            "end_date = #{endDate}, status = #{status}, progress = #{progress} " +
            "WHERE project_id = #{projectId}")
    void update(Project project);

    @Insert("INSERT INTO project_teams (project_id, team_id) VALUES (#{projectId}, #{teamId})")
    void insertProjectTeam(@Param("projectId") UUID projectId, @Param("teamId") UUID teamId);

    @Insert("INSERT INTO project_participants (project_id, employee_id) VALUES (#{projectId}, #{employeeId})")
    void insertProjectParticipant(@Param("projectId") UUID projectId, @Param("employeeId") UUID employeeId);

    @Delete("DELETE FROM project_teams WHERE project_id = #{projectId} AND team_id = #{teamId}")
    void removeProjectTeam(@Param("projectId") UUID projectId, @Param("teamId") UUID teamId);

    @Delete("DELETE FROM project_participants WHERE project_id = #{projectId} AND employee_id = #{employeeId}")
    void deleteProjectParticipant(@Param("projectId") UUID projectId, @Param("employeeId") UUID employeeId);

    @Delete("DELETE FROM project_teams WHERE project_id = #{projectId}")
    void deleteProjectTeams(UUID projectId);

    @Delete("DELETE FROM project_participants WHERE project_id = #{projectId}")
    void deleteProjectParticipants(UUID projectId);

    @Delete("DELETE FROM projects WHERE project_id = #{projectId}")
    void delete(UUID projectId);
}
