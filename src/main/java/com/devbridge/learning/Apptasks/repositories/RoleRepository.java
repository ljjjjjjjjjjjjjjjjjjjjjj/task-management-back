package com.devbridge.learning.Apptasks.repositories;

import com.devbridge.learning.Apptasks.models.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoleRepository {
    @Insert("INSERT INTO roles "
            + "(role_id, role_name)"
            + " VALUES (#{roleId}, #{roleName})")
    void create(Role role);

    @Select("SELECT * FROM roles WHERE role_id = #{roleId}")
    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
    })
    Optional<Role> findById(@Param("roleId") int roleId);

    @Select("SELECT * FROM roles WHERE role_name = #{roleName}")
    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
    })
    Optional<Role> findByName(@Param("roleName") String roleName);

    @Select("SELECT * FROM roles")
    @Results({
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name"),
    })
    List<Role> findAll();

    @Update("UPDATE roles SET role_name = #{roleName} WHERE role_id = #{roleId}")
    void update(Role role);

    @Delete("DELETE from roles where role_id = #{roleId}")
    void delete(@Param("roleId") int roleId);
}