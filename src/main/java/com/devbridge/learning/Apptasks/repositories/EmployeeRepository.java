package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Mapper
public interface EmployeeRepository {
    @Select("SELECT * FROM employees")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "roles", column = "employee_id", many = @Many(select = "getRolesByEmployeeId"))
    })
    List<Employee> findAll();

    @Select("SELECT * FROM employees WHERE employee_id = #{employeeId}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "roles", column = "employee_id", many = @Many(select = "getRolesByEmployeeId"))
    })
    Optional<Employee> findById(UUID employeeId);

    @Select({
            "<script>",
            "SELECT * FROM employees WHERE employee_id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "roles", column = "employee_id", many = @Many(select = "getRolesByEmployeeId"))
    })
    Set<Employee> findByIds(@Param("ids") Set<UUID> ids);

    @Select("SELECT employee_id FROM project_participants WHERE project_id = #{projectId}")
    Set<UUID> findEmployeeIdsByProjectId(UUID projectId);

    @Insert("INSERT INTO employees " +
            "(employee_id, first_name, last_name, email, password, team_id) " +
            "VALUES (#{employeeId}, #{firstName}, #{lastName}, #{email}, #{password}, #{teamId})")
    void create(Employee employee);

    @Update("UPDATE employees SET first_name = #{firstName}, last_name = #{lastName}, email = #{email}, password = #{password}, team_id = #{teamId} WHERE employee_id = #{employeeId}")
    void update(Employee employee);

    @Delete("DELETE FROM employees WHERE employee_id = #{employeeId}")
    void delete(UUID employeeId);

    @Select("SELECT * FROM employees WHERE email = #{email}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "roles", column = "employee_id", many = @Many(select = "getRolesByEmployeeId"))
    })
    Optional<Employee> findByEmail(String email);

    @Select("SELECT * FROM employees WHERE team_id = #{teamId}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "imageId", column = "image_id"),
            @Result(property = "roles", column = "employee_id", many = @Many(select = "getRolesByEmployeeId"))
    })
    List<Employee> findByTeamId(UUID teamId);

    @Select("SELECT r.role_id, r.role_name FROM roles r " +
            "INNER JOIN employee_roles er ON r.role_id = er.role_id " +
            "WHERE er.employee_id = #{employeeId}")
    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name")
    })
    Set<Role> getRolesByEmployeeId(UUID employeeId);

    @Insert("INSERT INTO employee_roles (employee_id, role_id) " +
            "VALUES (#{employeeId}, #{roleId})")
    void addRole(@Param("employeeId") UUID employeeId, @Param("roleId") int roleId);

    @Delete("DELETE FROM employee_roles " +
            "WHERE employee_id = #{employeeId} " +
            "AND role_id = #{roleId}")
    void removeRole(@Param("employeeId") UUID employeeId, @Param("roleId") int roleId);

    @Delete("DELETE FROM employee_roles " +
            "WHERE employee_id = #{employeeId}")
    void clearRoles(UUID employeeId);

    @Update("UPDATE employees " +
            "SET team_id = #{teamId} " +
            "WHERE employee_id = #{employeeId}")
    void assignToTeam(@Param("employeeId") UUID employeeId, @Param("teamId") UUID teamId);

    @Update("UPDATE employees " +
            "SET team_id = NULL " +
            "WHERE employee_id = #{employeeId}")
    void removeFromTeam(@Param("employeeId") UUID employeeId);

    @Update("UPDATE employees " +
            "SET image_id = #{imageId} " +
            "WHERE employee_id = #{employeeId}")
    void updateImageIdForEmployee(UUID employeeId, UUID imageId);
}
