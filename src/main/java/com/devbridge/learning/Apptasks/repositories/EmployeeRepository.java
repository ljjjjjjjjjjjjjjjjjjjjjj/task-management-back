package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface EmployeeRepository {
    @Select("SELECT * FROM employees")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password")
    })
    List<Employee> findAll();

    @Select("SELECT * FROM employees WHERE employee_id = #{employeeId}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password")
    })
    Optional<Employee> findById(UUID employeeId);

    @Insert("INSERT INTO employees " +
            "(employee_id, first_name, last_name, email, password) " +
            "VALUES (#{employeeId}, #{firstName}, #{lastName}, #{email}, #{password})")
    void create(Employee employee);

    @Update("UPDATE employees SET first_name = #{firstName}, last_name = #{lastName}, email = #{email}, password = #{password} WHERE employee_id = #{employeeId}")
    void update(Employee employee);

    @Delete("DELETE FROM employees WHERE employee_id = #{employeeId}")
    void delete(UUID employeeId);

    @Select("SELECT * FROM employees WHERE email = #{email}")
    @Results({
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password")
    })
    Optional<Employee> findByEmail(String email);
}