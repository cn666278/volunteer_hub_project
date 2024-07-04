package com.wsa;

import com.wsa.controller.UserController;
import com.wsa.model.User;
import com.wsa.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    public UserControllerTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        User user = new User();
        user.setId(1L);

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        List<User> users = userController.getUsers();

        assertThat(users).isNotEmpty();
    }
}
