package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.dtos.TeamDto;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final static String TEAM_DELETED = "Team deleted successfully";

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{teamId}")
    public TeamDto getTeamById(@PathVariable UUID teamId) {
        return teamService.getTeamById(teamId);
    }

    @GetMapping("/leader/{employeeId}")
    public TeamDto getTeamByLeaderId(@PathVariable UUID employeeId) {
        return teamService.getTeamByLeaderId(employeeId);
    }

    @GetMapping("/member/{employeeId}")
    public TeamDto getTeamByMemberId(@PathVariable UUID employeeId) {
        return teamService.getTeamByMemberId(employeeId);
    }

    @GetMapping("/employee/{employeeId}")
    public TeamDto getTeamByEmployeeId(@PathVariable UUID employeeId) {
        return teamService.getTeamByEmployeeId(employeeId);
    }

    @PostMapping
    public TeamDto createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/{teamId}")
    public TeamDto updateTeam(@PathVariable UUID teamId, @RequestBody Team team) {
        return teamService.updateTeam(teamId, team);
    }

    @PutMapping("/{teamId}/leader")
    public TeamDto updateTeamLeader(@PathVariable UUID teamId, @RequestParam UUID employeeId) {
        return teamService.updateTeamLeader(teamId, employeeId);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable UUID teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok(TEAM_DELETED);
    }

}