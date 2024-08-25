package com.wsa.lightweightmockmvctests;

import com.wsa.controller.UserController;
import com.wsa.model.*;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetLoginUserInfo_Success() throws Exception {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("JohnDoe");

        when(userService.getUserInfoByUsername(any(String.class))).thenReturn(userInfo);

        // When & Then
        mockMvc.perform(post("/getLoginUserInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"JohnDoe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.username").value("JohnDoe"));
    }

    @Test
    public void testGetUserList_Success() throws Exception {
        // Given
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("JohnDoe");
        userInfos.add(userInfo);

        when(userService.getAllUsers()).thenReturn(userInfos);

        // When & Then
        mockMvc.perform(get("/admin/user/getUserList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list[0].username").value("JohnDoe"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("JohnDoe");

        when(userService.getUserInfoById(anyLong())).thenReturn(userInfo);

        // When & Then
        mockMvc.perform(post("/admin/user/getUserById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.username").value("JohnDoe"));
    }

    @Test
    public void testAddUser_Success() throws Exception {
        // Given
        // Mock the service behavior

        // When & Then
        mockMvc.perform(post("/admin/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"JohnDoe\", \"password\":\"password123\", \"roleId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("add success"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        // Given
        // Mock the service behavior

        // When & Then
        mockMvc.perform(post("/admin/user/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"username\":\"JohnDoe\", \"roleId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("update success"));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        // Given
        // Mock the service behavior

        // When & Then
        mockMvc.perform(post("/admin/user/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("delete success"));
    }

    @Test
    public void testGetRatingsByVolunteerId_Success() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);

        List<VolunteerRating> ratings = new ArrayList<>();
        VolunteerRating rating = new VolunteerRating();
        rating.setRating(5);
        ratings.add(rating);

        when(volunteerService.getVolunteerByUserId(anyLong())).thenReturn(volunteer);
        when(userService.getRatingsByVolunteerId(anyLong())).thenReturn(ratings);

        // When & Then
        mockMvc.perform(get("/user/1/ratings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].rating").value(5));
    }

    @Test
    public void testChangePassword_Success() throws Exception {
        // Given
        when(userService.changePassword(anyLong(), any(String.class), any(String.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"loginId\":1, \"currentPassword\":\"oldPass\", \"newPassword\":\"newPass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Password changed successfully"));
    }

    @Test
    public void testChangePassword_Failure() throws Exception {
        // Given
        when(userService.changePassword(anyLong(), any(String.class), any(String.class))).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"loginId\":1, \"currentPassword\":\"oldPass\", \"newPassword\":\"newPass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.data").value("Current password is incorrect"));
    }
}
