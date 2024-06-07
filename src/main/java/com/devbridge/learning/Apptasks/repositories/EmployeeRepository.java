package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface EmployeeRepository {
    @Select("SELECT * FROM employees")
    List<Employee> findAll();

    @Select("SELECT * FROM employees WHERE employee_id = #{employeeId}")
    Employee findById(UUID employeeId);

    @Insert("INSERT INTO employees (employee_id, first_name, last_name, email, password) VALUES (#{employeeId}, #{firstName},#{lastName}, #{email}, #{password})")
    void save(Employee employee);

    @Update("UPDATE employees SET first_name = #{firstName}, last_name = #{lastName}, email = #{email}, password = #{password} WHERE employee_id = #{employeeId}")
    void update(Employee employee);

    @Delete("DELETE FROM employees WHERE employee_id = #{employeeId}")
    void delete(UUID employeeId);

    @Select("SELECT * FROM employees WHERE email = #{email}")
    Employee findByEmail(String email);
}