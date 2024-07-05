package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id not found";

    public TeamDto toDto(Team team) {
        if (team == null) {
            return null;
        }

        Set<UUID> memberIds = teamRepository.getMembersIdsByTeamId(team.getTeamId())
                .stream().collect(Collectors.toSet());

        Set<EmployeeDto> members = memberIds.stream()
                .map(id -> employeeRepository.findById(id)
                        .map(EmployeeMapper::toDto)
                        .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND)))
                .collect(Collectors.toSet());

        return TeamDto.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .teamLeaderId(team.getTeamLeaderId())
                .teamMembers(members)
                .build();
    }

    public Team toEntity(TeamDto teamDto) {
        if (teamDto == null) {
            return null;
        }

        return Team.builder()
                .teamId(teamDto.getTeamId())
                .teamName(teamDto.getTeamName())
                .teamLeaderId(teamDto.getTeamLeaderId())
                .build();
    }

}
