package com.wsa.itemmockmvctests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsa.controller.UserController;
import com.wsa.model.*;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private VolunteerService volunteerService;

    @Test
    public void shouldReturnLoginUserInfo() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testuser");
        userInfo.setId(1L);
        userInfo.setEmail("testuser@example.com");

        when(userService.getUserInfoByUsername("testuser")).thenReturn(userInfo);

        UserRequest request = new UserRequest();
        request.setUsername("testuser");

        mockMvc.perform(post("/getLoginUserInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("testuser@example.com"));
    }

    @Test
    public void shouldReturnUserList() throws Exception {
        UserInfo user1 = new UserInfo();
        user1.setId(1L);
        user1.setUsername("user1");

        UserInfo user2 = new UserInfo();
        user2.setId(2L);
        user2.setUsername("user2");

        List<UserInfo> userInfos = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(userInfos);

        mockMvc.perform(get("/admin/user/getUserList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.list", hasSize(2)))
                .andExpect(jsonPath("$.data.list[0].username").value("user1"))
                .andExpect(jsonPath("$.data.list[1].username").value("user2"));
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("testuser");

        when(userService.getUserInfoById(1L)).thenReturn(userInfo);

        UserRequest request = new UserRequest();
        request.setId(1L);

        mockMvc.perform(post("/admin/user/getUserById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    public void shouldAddUser() throws Exception {
        UserReq request = new UserReq();
        request.setUsername("newuser");
        request.setPassword("password123");
        request.setEmail("newuser@example.com");
        request.setPhone("1234567890");

        mockMvc.perform(post("/admin/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("add success"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        UserReq request = new UserReq();
        request.setId(1L);
        request.setUsername("updateduser");
        request.setEmail("updateduser@example.com");
        request.setPhone("0987654321");

        mockMvc.perform(post("/admin/user/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("update success"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        UserReq request = new UserReq();
        request.setId(1L);

        mockMvc.perform(post("/admin/user/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("delete success"));
    }

    @Test
    public void shouldChangePassword() throws Exception {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setLoginId(1L);
        request.setCurrentPassword("oldpassword");
        request.setNewPassword("newpassword");

        when(userService.changePassword(1L, "oldpassword", "newpassword")).thenReturn(true);

        mockMvc.perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed successfully"));
    }

    @Test
    public void shouldReturnVolunteerRatings() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        when(volunteerService.getVolunteerByUserId(1L)).thenReturn(volunteer);

        VolunteerRating rating1 = new VolunteerRating();
        rating1.setRating(5);

        VolunteerRating rating2 = new VolunteerRating();
        rating2.setRating(4);

        List<VolunteerRating> ratings = Arrays.asList(rating1, rating2);

        when(userService.getRatingsByVolunteerId(1L)).thenReturn(ratings);

        mockMvc.perform(get("/user/1/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].rating").value(5))
                .andExpect(jsonPath("$.data[1].rating").value(4));
    }

    @Test
    public void shouldReturnVolunteerInfo() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        when(volunteerService.getVolunteerByUserId(1L)).thenReturn(volunteer);

        VolunteerInfo info1 = new VolunteerInfo();
        info1.setInfoBody("Info 1");

        VolunteerInfo info2 = new VolunteerInfo();
        info2.setInfoBody("Info 2");

        List<VolunteerInfo> volunteerInfos = Arrays.asList(info1, info2);

        when(userService.getVolunteerInfoByVolunteerId(1L)).thenReturn(volunteerInfos);

        mockMvc.perform(get("/user/1/volunteerinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].info").value("Info 1"))
                .andExpect(jsonPath("$.data[1].info").value("Info 2"));
    }
}
