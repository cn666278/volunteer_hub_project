package com.wsa.SimpleUnitTests;

import com.wsa.mapper.RoleMapper;
import com.wsa.model.Role;
import com.wsa.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSelectRoleById() {
        // Arrange
        Long roleId = 1L;
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName("Admin");

        when(roleMapper.selectRoleById(roleId)).thenReturn(role);

        // Act
        Role result = roleService.selectRoleById(roleId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRoleName()).isEqualTo("Admin");
        verify(roleMapper).selectRoleById(roleId);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        Role role1 = new Role();
        Role role2 = new Role();

        when(roleMapper.getAllRoles()).thenReturn(Arrays.asList(role1, role2));

        // Act
        List<Role> roles = roleService.getAllRoles();

        // Assert
        assertThat(roles).hasSize(2);
        verify(roleMapper).getAllRoles();
    }

    @Test
    void testAddRole() {
        // Arrange
        Role role = new Role();
        role.setRoleName("New Role");

        // Act
        roleService.addRole(role);

        // Assert
        verify(roleMapper).addRole(role);
    }

    @Test
    void testUpdateRole() {
        // Arrange
        Role role = new Role();
        role.setRoleName("Updated Role");

        // Act
        roleService.updateRole(role);

        // Assert
        verify(roleMapper).updateRole(role);
    }

    @Test
    void testDeleteRole() {
        // Arrange
        Role role = new Role();
        role.setRoleId(1L);

        // Act
        roleService.deleteRole(role);

        // Assert
        verify(roleMapper).deleteRole(role);
    }
}
