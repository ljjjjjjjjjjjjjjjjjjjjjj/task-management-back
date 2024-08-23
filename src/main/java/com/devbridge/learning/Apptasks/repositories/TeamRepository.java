package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Mapper
public interface TeamRepository {

    @Select("SELECT * FROM teams")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    List<Team> findAll();

    @Select("SELECT * FROM teams " +
            "WHERE team_id = #{teamId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    Optional<Team> findById(UUID teamId);


    @Select({
            "<script>",
            "SELECT * FROM teams WHERE team_id IN",
            "<foreach item='teamId' collection='teamIds' open='(' separator=',' close=')'>",
            "#{teamId}",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    Set<Team> findByIds(@Param("teamIds") Set<UUID> teamIds);

    @Select("SELECT team_id, team_name, team_leader_id FROM teams " +
            "WHERE team_leader_id = #{teamLeaderId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    Optional<Team> findByLeaderId(UUID teamLeaderId);

    @Select("SELECT t.team_id, t.team_name, t.team_leader_id FROM teams t " +
            "JOIN team_members tm ON t.team_id = tm.team_id " +
            "WHERE tm.employee_id = #{teamMemberId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    Optional<Team> findByMemberId(UUID teamMemberId);

    @Select("SELECT t.team_id, t.team_name, t.team_leader_id FROM teams t " +
            "JOIN team_members tm ON t.team_id = tm.team_id " +
            "WHERE t.team_leader_id = #{employeeId} OR tm.employee_id = #{employeeId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findByTeamId"))
    })
    Optional<Team> findByEmployeeId(UUID employeeId);

    @Select("SELECT team_id FROM project_teams WHERE project_id = #{projectId}")
    Set<UUID> findTeamIdsByProjectId(UUID projectId);

    @Select("SELECT team_id FROM teams WHERE team_leader_id = #{teamLeaderId}")
    List<UUID> findTeamIdsByLeaderId(UUID teamLeaderId);

    @Insert("INSERT INTO teams (team_id, team_name, team_leader_id) " +
            "VALUES (#{teamId}, #{teamName}, #{teamLeaderId})")
    void create(Team team);

    @Update("UPDATE teams SET team_name = #{teamName}, team_leader_id = #{teamLeaderId} " +
            "WHERE team_id = #{teamId}")
    void update(Team team);

    @Delete("DELETE FROM teams WHERE team_id = #{teamId}")
    void delete(UUID teamId);

    @Insert("INSERT INTO team_members (team_id, employee_id) " +
            "VALUES (#{teamId}, #{employeeId})")
    void addMember(@Param("teamId") UUID teamId, @Param("employeeId") UUID employeeId);

    @Delete("DELETE FROM team_members " +
            "WHERE team_id = #{teamId} AND employee_id = #{employeeId}")
    void removeMember(@Param("teamId") UUID teamId, @Param("employeeId") UUID employeeId);
}
