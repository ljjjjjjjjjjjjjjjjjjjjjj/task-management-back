package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.EmployeeDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.EmployeeMapper;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.models.Team;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.RoleRepository;
import com.devbridge.learning.Apptasks.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final TeamRepository teamRepository;

    public static final String EMPLOYEE_NOT_FOUND = "Employee by the given id not found";
    private static final String EMPLOYEE_ALREADY_IN_TEAM = "Employee is already a member of the team";
    private static final String EMPLOYEE_NOT_IN_TEAM = "Employee is not a member of the team";
    public static final String ROLE_NOT_FOUND = "Role by the given id not found";
    public static final String ROLE_NEEDED = "Employee must have at least one role.";
    public static final String ROLE_ALREADY_ASSIGNED = "This role is already assigned to the employee";
    public static final String ROLE_NOT_ASSIGNED = "This role is not assigned to the employee";
    private final static String TEAM_NOT_FOUND = "Team with given id not found";

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(UUID employeeId) {
        Employee existingEmployee  = validateEmployeeId(employeeId);
        return EmployeeMapper.toDto(existingEmployee);
    }

    public List<EmployeeDto> getEmployeesByTeamId (UUID teamId) {
        Team existingTeam = validateTeamId(teamId);
        List<Employee> employees = employeeRepository.findByTeamId(teamId);

        return employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        Employee existingEmployee  = validateEmployeeId(employeeId);

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());

        employeeRepository.update(existingEmployee);
        return EmployeeMapper.toDto(existingEmployee);
    }

    public EmployeeDto addEmployeeRole(UUID employeeId, int roleId) {
        Employee existingEmployee  = validateEmployeeId(employeeId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));

        if (existingEmployee.getRoles().contains(role)) {
            throw new IllegalArgumentException(ROLE_ALREADY_ASSIGNED);
        }

        employeeRepository.addRole(existingEmployee.getEmployeeId(), roleId);

        existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        return EmployeeMapper.toDto(existingEmployee);
    }

    public EmployeeDto removeEmployeeRole(UUID employeeId, int roleId) {
        Employee existingEmployee  = validateEmployeeId(employeeId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));

        Set<Role> roles = existingEmployee.getRoles();
        if (!roles.contains(role)) {
            throw new IllegalArgumentException(ROLE_NOT_ASSIGNED);
        }

        roles.remove(role);
        if (roles.isEmpty()) {
            throw new IllegalArgumentException(ROLE_NEEDED);
        }

        employeeRepository.removeRole(existingEmployee.getEmployeeId(), roleId);
        existingEmployee  = validateEmployeeId(employeeId);

        return EmployeeMapper.toDto(existingEmployee);
    }

    public EmployeeDto addToTeam(UUID employeeId, UUID teamId) {
        Employee existingEmployee = validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);

        if (existingEmployee.getTeamId() != null && existingEmployee.getTeamId().equals(teamId)) {
            throw new IllegalArgumentException(EMPLOYEE_ALREADY_IN_TEAM);
        }

        employeeRepository.assignToTeam(employeeId, teamId);
        teamRepository.addMember(teamId, employeeId);
        existingEmployee.setTeamId(teamId);

        return EmployeeMapper.toDto(existingEmployee);
    }


    public EmployeeDto removeFromTeam(UUID employeeId, UUID teamId) {
        Employee existingEmployee = validateEmployeeId(employeeId);
        Team existingTeam = validateTeamId(teamId);

        if (existingEmployee.getTeamId() == null || !existingEmployee.getTeamId().equals(teamId)) {
            throw new IllegalArgumentException(EMPLOYEE_NOT_IN_TEAM);
        }

        employeeRepository.removeFromTeam(employeeId);
        teamRepository.removeMember(teamId, employeeId);
        existingEmployee.setTeamId(null);

        return EmployeeMapper.toDto(existingEmployee);
    }



    public void deleteEmployee(UUID employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        employeeRepository.clearRoles(employeeId);

        employeeRepository.delete(existingEmployee.getEmployeeId());
    }

    private Employee validateEmployeeId(UUID employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));
        return existingEmployee;
    }

    private Team validateTeamId(UUID teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }
}
