package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.models.Team;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TeamMapper {

    public TeamDto toDto(Team team) {
        if (team == null) {
            return null;
        }

        Set<EmployeeDto> members = team.getTeamMembers().stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toSet());

        return TeamDto.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .teamLeaderId(team.getTeamLeaderId())
                .teamMembers(members)
                .build();
    }

}
