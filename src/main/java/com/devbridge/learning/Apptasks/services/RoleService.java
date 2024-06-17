package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public static final String ROLE_NOT_FOUND = "Role by the given id not found";

    public Role getRole(int roleId) {
        return (roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND)));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        roleRepository.create(role);
        return role;
    }

    public Role updateRole(int roleId, Role newRole) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));

        roleRepository.update(role);
        return role;
    }

    public void deleteRole(int roleId) {
        roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_FOUND));
        roleRepository.delete(roleId);
    }

}