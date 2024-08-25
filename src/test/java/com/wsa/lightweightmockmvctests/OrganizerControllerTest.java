package com.wsa.lightweightmockmvctests;

import com.wsa.controller.OrganizerController;
import com.wsa.model.Organizer;
import com.wsa.model.OrganizerRequest;
import com.wsa.model.UserInfo;
import com.wsa.service.OrganizerService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizerController.class)
public class OrganizerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrganizerService organizerService;

    @InjectMocks
    private OrganizerController organizerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(organizerController).build();
    }

    @Test
    public void testGetUserByOrganizerId_Success() throws Exception {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("JohnDoe");

        when(organizerService.getUserInfoByOrganizerId(anyLong())).thenReturn(userInfo);

        // When & Then
        mockMvc.perform(post("/organizer/getUserByOrganizerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"organizerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.username").value("JohnDoe"));
    }

    @Test
    public void testGetUserByOrganizerId_NotFound() throws Exception {
        // Given
        when(organizerService.getUserInfoByOrganizerId(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/organizer/getUserByOrganizerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"organizerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Organizer not found for organizerId: 1"));
    }

    @Test
    public void testGetAllOrganizers_Success() throws Exception {
        // Given
        List<Organizer> organizers = new ArrayList<>();
        Organizer organizer1 = new Organizer();
        organizer1.setId(1L);
        organizer1.setOrganizationName("Org1");
        organizers.add(organizer1);

        when(organizerService.getOrganizersByPage(anyInt(), anyInt())).thenReturn(organizers);

        // When & Then
        mockMvc.perform(get("/organizer/getAllOrganizers")
                        .param("current", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].organizationName").value("Org1"));
    }

    @Test
    public void testGetAllOrganizers_NotFound() throws Exception {
        // Given
        when(organizerService.getOrganizersByPage(anyInt(), anyInt())).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/organizer/getAllOrganizers")
                        .param("current", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("No organizers found"));
    }
}
