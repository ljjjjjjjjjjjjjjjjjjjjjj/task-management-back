package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.dtos.TeamNameDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.mappers.TeamMapper;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamMapper teamMapper;

    private final static String TEAM_NOT_FOUND = "Team with given id not found";
    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id not found";


    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    public TeamDto getTeamById(UUID teamId) {
        Team existingTeam = validateTeamId(teamId);
        return teamMapper.toDto(existingTeam);
    }

    public Set<TeamNameDto> getTeamNameDtosByIds(Set<UUID> teamIds) {
        if (teamIds == null || teamIds.isEmpty()) {
            return Set.of();
        }

        Set<Team> teams = teamRepository.findByIds(teamIds);
        return TeamMapper.toNameDtoSet(teams);
    }

    public TeamDto getTeamByLeaderId(UUID employeeId) {
        Team existingTeam = teamRepository.findByLeaderId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
        return teamMapper.toDto(existingTeam);
    }

    public TeamDto getTeamByMemberId(UUID employeeId) {
        Team existingTeam = teamRepository.findByMemberId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
        return teamMapper.toDto(existingTeam);
    }

    public TeamDto getTeamByEmployeeId(UUID employeeId) {
        Team existingTeam = teamRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
        return teamMapper.toDto(existingTeam);
    }

    public TeamDto createTeam(Team team) {
        validateEmployeeId(team.getTeamLeaderId());
        team.setTeamId(UUID.randomUUID());
        teamRepository.create(team);
        return teamMapper.toDto(team);
    }

    public TeamDto updateTeam(UUID teamId, Team team) {
        Team existingTeam = validateTeamId(teamId);
        validateEmployeeId(team.getTeamLeaderId());

        existingTeam.setTeamName(team.getTeamName());
        existingTeam.setTeamLeaderId(team.getTeamLeaderId());

        teamRepository.update(existingTeam);
        return teamMapper.toDto(existingTeam);
    }

    public TeamDto updateTeamLeader(UUID teamId, UUID employeeId) {
        validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);
        existingTeam.setTeamLeaderId(employeeId);
        teamRepository.update(existingTeam);
        return teamMapper.toDto(existingTeam);
    }

    public void deleteTeam(UUID teamId) {
        validateTeamId(teamId);
        teamRepository.delete(teamId);
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
