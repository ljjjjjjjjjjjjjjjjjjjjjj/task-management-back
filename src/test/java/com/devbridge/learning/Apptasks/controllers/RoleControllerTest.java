package com.devbridge.learning.Apptasks.controllers;

import com.devbridge.learning.Apptasks.models.Role;
import com.devbridge.learning.Apptasks.services.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetRoles() throws Exception {
        Role role = new Role(1, "USER");
        when(roleService.getRoles()).thenReturn(List.of(role));

        mockMvc.perform(get("/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"roleId\":1,\"roleName\":\"USER\"}]"));
    }

    @Test
    public void testGetRoleById() throws Exception {
        int roleId = 1;
        Role role = new Role(roleId, "USER");
        when(roleService.getRole(eq(roleId))).thenReturn(role);

        mockMvc.perform(get("/roles/{roleId}", roleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"roleId\":1,\"roleName\":\"USER\"}"));
    }

    @Test
    public void testCreateRole() throws Exception {
        Role role = new Role(1, "USER");
        when(roleService.createRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"roleId\":1,\"roleName\":\"USER\"}"));
    }

    @Test
    public void testUpdateRole() throws Exception {
        int roleId = 1;
        Role role = new Role(roleId, "ADMIN");
        when(roleService.updateRole(eq(roleId), any(Role.class))).thenReturn(role);

        mockMvc.perform(put("/roles/{roleId}", roleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"roleId\":1,\"roleName\":\"ADMIN\"}"));
    }

    @Test
    public void testDeleteRole() throws Exception {
        int roleId = 1;
        mockMvc.perform(delete("/roles/{roleId}", roleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}