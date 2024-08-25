package com.wsa.SimpleUnitTests;

import com.wsa.exception.ResourceNotFoundException;
import com.wsa.mapper.*;
import com.wsa.model.*;
import com.wsa.service.UserService;
import com.wsa.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthorityMapper authorityMapper;

    @Mock
    private OrganizerMapper organizerMapper;

    @Mock
    private VolunteerMapper volunteerMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUsername("user1");
        users.add(user);
        when(userMapper.selectAllUsers()).thenReturn(users);

        // Act
        List<UserInfo> userInfos = userService.getAllUsers();

        // Assert
        assertThat(userInfos).hasSize(1);
        assertThat(userInfos.get(0).getUsername()).isEqualTo("user1");
        verify(userMapper, times(1)).selectAllUsers();
    }

    @Test
    void testAddUser() {
        // Arrange
        UserReq request = new UserReq();
        request.setLoginId(555L);
        request.setUsername("user1");
        request.setPassword("password1");
        request.setRoleId(1L);

        Role role = new Role();
        role.setRoleName("ROLE_USER");

        when(roleMapper.selectRoleById(request.getRoleId())).thenReturn(role);

        // Act
        userService.addUser(request);

        // Assert
        verify(userMapper).addUser(any(User.class));
        verify(authorityMapper).addAuthority(any(Authority.class));
        verify(organizerMapper).addOrganizer(any(Organizer.class));
    }

    @Test
    void testUpdateUser() {
        // Arrange
        UserReq request = new UserReq();
        request.setId(1L);
        request.setUsername("updatedUser");
        request.setRoleName(1L);

        Role role = new Role();
        role.setRoleName("ROLE_USER");

        when(roleMapper.selectRoleById(request.getRoleName())).thenReturn(role);

        // Act
        userService.updateUser(request);

        // Assert
        verify(userMapper).updateUser(any(User.class));
        verify(authorityMapper).updateAuthority(any(Authority.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        UserReq request = new UserReq();
        request.setId(1L);

        // Act
        userService.deleteUser(request);

        // Assert
        verify(userMapper).deleteUser(request);
        verify(authorityMapper).deleteAuthority(request);
    }

    @Test
    void testChangePassword_UserNotFound() {
        // Arrange
        Long loginId = 1L;
        when(userMapper.findById(loginId)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.changePassword(loginId, "currentPassword", "newPassword"));
    }

    @Test
    void testChangePassword_Success() {
        // Arrange
        Long loginId = 1L;
        User user = new User();
        user.setId(loginId);
        user.setPassword("encodedPassword");

        when(userMapper.findById(loginId)).thenReturn(user);

        // Act
        boolean result = userService.changePassword(loginId, "currentPassword", "newPassword");

        // Assert
        assertThat(result).isTrue();
        verify(userMapper).updateUser(user);
    }
}
