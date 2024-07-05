package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.mappers.TeamMapper;
import com.devbridge.learning.Apptasks.models.Task;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamMapper teamMapper;

    private final static String TEAM_NOT_FOUND = "Team with given id not found";
    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id not found";
    private static final String EMPLOYEE_ALREADY_IN_TEAM = "Employee is already a member of the team";
    private static final String EMPLOYEE_NOT_IN_TEAM = "Employee is not a member of the team";


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public TeamDto getTeamById(UUID teamId) {
        return teamRepository.findWithMembersById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    public TeamDto getTeamByLeaderId(UUID employeeId) {
        return teamRepository.findWithMembersByLeaderId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    public TeamDto getTeamByMemberId(UUID employeeId) {
        return teamRepository.findWithMembersByMemberId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    public Team createTeam(Team team) {
        validateEmployeeId(team.getTeamLeaderId());
        team.setTeamId(UUID.randomUUID());
        teamRepository.create(team);
        return team;
    }

    public Team updateTeam(UUID teamId, Team team) {
        Team existingTeam = validateTeamId(teamId);
        validateEmployeeId(team.getTeamLeaderId());

        existingTeam.setTeamName(team.getTeamName());
        existingTeam.setTeamLeaderId(team.getTeamLeaderId());

        teamRepository.update(existingTeam);
        return existingTeam;
    }

    public Team updateTeamLeader(UUID teamId, UUID employeeId) {
        validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);
        existingTeam.setTeamLeaderId(employeeId);
        teamRepository.update(existingTeam);
        return existingTeam;
    }

    public void deleteTeam(UUID teamId) {
        validateTeamId(teamId);
        teamRepository.delete(teamId);
    }

    public EmployeeDto addMember(UUID teamId, UUID employeeId) {
        validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);

        List<UUID> memberIds = teamRepository.getMembersIdsByTeamId(teamId);
        if (memberIds.contains(employeeId)) {
            throw new IllegalArgumentException(EMPLOYEE_ALREADY_IN_TEAM);
        }

        teamRepository.addMember(teamId, employeeId);
        EmployeeDto employeeDto = employeeRepository.findById(employeeId)
                .map(EmployeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return employeeDto;
    }

    public EmployeeDto removeMember(UUID teamId, UUID employeeId) {
        validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);

        List<UUID> memberIds = teamRepository.getMembersIdsByTeamId(teamId);
        if (!memberIds.contains(employeeId)) {
            throw new IllegalArgumentException(EMPLOYEE_NOT_IN_TEAM);
        }

        teamRepository.removeMember(teamId, employeeId);

        EmployeeDto employeeDto = employeeRepository.findById(employeeId)
                .map(EmployeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return employeeDto;
    }

    private void validateEmployeeId(UUID employeeId) {
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND);
        }
    }

    private Team validateTeamId(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }
}