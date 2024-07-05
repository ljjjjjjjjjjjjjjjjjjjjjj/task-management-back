package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.models.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TeamRepository {

    @Select("SELECT * FROM teams")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
    })
    List<Team> findAll();

    @Select("SELECT * FROM teams " +
            "WHERE team_id = #{teamId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id")
    })
    Optional<Team> findById(UUID teamId);

    @Select("SELECT t.team_id, t.team_name, t.team_leader_id FROM teams t " +
            "WHERE t.team_id = #{teamId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeDtosByTeamId"))
    })
    Optional<TeamDto> findWithMembersById(UUID teamId);

    @Select("SELECT t.team_id, t.team_name, t.team_leader_id FROM teams t " +
            "WHERE t.team_id = #{teamId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeDtosByTeamId"))
    })
    Optional<TeamDto> findWithMembersByLeaderId(UUID employeeId);

    @Select("SELECT t.team_id, t.team_name, t.team_leader_id FROM teams t " +
            "JOIN team_members tm ON t.team_id = tm.team_id " +
            "WHERE tm.employee_id = #{employeeId}")
    @Results({
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamLeaderId", column = "team_leader_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select =
                    "com.devbridge.learning.Apptasks.repositories.EmployeeRepository.findEmployeeDtosByTeamId"))
    })
    Optional<TeamDto> findWithMembersByMemberId(UUID employeeId);

    @Insert("INSERT INTO teams (team_id, team_name, team_leader_id) " +
            "VALUES (#{teamId}, #{teamName}, #{teamLeaderId})")
    void create(Team team);

    @Update("UPDATE teams SET team_name = #{teamName}, team_leader_id = #{teamLeaderId} " +
            "WHERE team_id = #{teamId}")
    void update(Team team);

    @Delete("DELETE FROM teams WHERE team_id = #{teamId}")
    void delete(UUID teamId);

    @Select("SELECT employee_id FROM team_members WHERE team_id = #{teamId}")
    List<UUID> getMembersIdsByTeamId(UUID teamId);

    @Insert("INSERT INTO team_members (team_id, employee_id) " +
            "VALUES (#{teamId}, #{employeeId})")
    void addMember(@Param("teamId") UUID teamId, @Param("employeeId") UUID employeeId);

    @Delete("DELETE FROM team_members " +
            "WHERE team_id = #{teamId} AND employee_id = #{employeeId}")
    void removeMember(@Param("teamId") UUID teamId, @Param("employeeId") UUID employeeId);
}
