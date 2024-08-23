package com.devbridge.learning.Apptasks.mappers;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.dtos.TeamNameDto;
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

    public static TeamNameDto toNameDto(Team team) {
        if (team == null) {
            return null;
        }

        return TeamNameDto.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .build();
    }

    public static Set<TeamNameDto> toNameDtoSet(Set<Team> teams) {
        if (teams == null) {
            return null;
        }
        return teams.stream()
                .map(TeamMapper::toNameDto)
                .collect(Collectors.toSet());
    }

}
