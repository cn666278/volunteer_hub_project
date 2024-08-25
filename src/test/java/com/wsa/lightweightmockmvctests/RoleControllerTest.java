package com.wsa.lightweightmockmvctests;

import com.wsa.controller.RoleController;
import com.wsa.model.Role;
import com.wsa.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
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
    public void testGetRoleList_Success() throws Exception {
        // Given
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("Admin");
        roles.add(role);

        when(roleService.getAllRoles()).thenReturn(roles);

        // When & Then
        mockMvc.perform(get("/admin/role/getRoleList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].roleName").value("Admin"));
    }

    @Test
    public void testGetRoleList_NotFound() throws Exception {
        // Given
        when(roleService.getAllRoles()).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/admin/role/getRoleList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("getRoleList : not found!"));
    }

    @Test
    public void testAddRole_Success() throws Exception {
        // Given
        doNothing().when(roleService).addRole(any(Role.class));

        // When & Then
        mockMvc.perform(post("/admin/role/addRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("add success"));
    }

    @Test
    public void testUpdateRole_Success() throws Exception {
        // Given
        doNothing().when(roleService).updateRole(any(Role.class));

        // When & Then
        mockMvc.perform(post("/admin/role/updateRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"roleName\":\"Super Admin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("update success"));
    }

    @Test
    public void testDeleteRole_Success() throws Exception {
        // Given
        doNothing().when(roleService).deleteRole(any(Role.class));

        // When & Then
        mockMvc.perform(post("/admin/role/deleteRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("delete success"));
    }
}
