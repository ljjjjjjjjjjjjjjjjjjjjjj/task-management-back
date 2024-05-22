package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface EmployeeRepository {
    @Select("SELECT * FROM tasks.employees")
    List<Employee> findAll();

    @Select("SELECT * FROM tasks.employees WHERE employee_id = #{employeeId}")
    Employee findById(UUID employeeId);

    @Insert("INSERT INTO tasks.employees (employee_id, name, email, password) VALUES (#{employeeId}, #{name}, #{email}, #{password})")
    void save(Employee employee);

    @Update("UPDATE tasks.employees SET name = #{name}, email = #{email}, password = #{password} WHERE employee_id = #{employeeId}")
    void update(Employee employee);

    @Delete("DELETE FROM tasks.employees WHERE employee_id = #{employeeId}")
    void delete(UUID employeeId);

    @Select("SELECT * FROM tasks.employees WHERE email = #{email}")
    Employee findByEmail(String email);
}