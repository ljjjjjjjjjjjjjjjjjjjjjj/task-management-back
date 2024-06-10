package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping()
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/{roleId}")
    public Role getRole(@PathVariable int roleId) {
        return roleService.getRole(roleId);
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{roleId}")
    public Role updateRole(@PathVariable int roleId, @RequestBody Role role) {
        return roleService.updateRole(roleId, role);
    }

    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable int roleId) {
        roleService.deleteRole(roleId);
    }

}
