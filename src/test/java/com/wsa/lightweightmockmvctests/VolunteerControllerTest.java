package com.wsa.lightweightmockmvctests;

import com.github.pagehelper.PageInfo;
import com.wsa.controller.VolunteerController;
import com.wsa.model.Credential;
import com.wsa.model.Event;
import com.wsa.model.ResultVO;
import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import com.wsa.service.EventService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private VolunteerController volunteerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerController).build();
    }

    @Test
    public void testGetVolunteersByEventId_Success() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setRoleId(1L);
        List<Volunteer> volunteerList = new ArrayList<>();
        volunteerList.add(volunteer);

        PageInfo<Volunteer> pageInfo = new PageInfo<>(volunteerList);
        when(volunteerService.getVolunteersByEventId(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(pageInfo);

        // When & Then
        mockMvc.perform(get("/volunteer/getVolunteersByEventId")
                        .param("eventId", "1")
                        .param("status", "active")
                        .param("pageNum", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.list[0].roleName").value("Event Coordinator"));
    }

    @Test
    public void testSubmitComment_Success() throws Exception {
        // Given
        Event event = new Event();
        event.setPointsAwarded(10);
        when(eventService.getEventById(anyLong())).thenReturn(event);
        doNothing().when(volunteerService).submitComment(any(SubmitCommentRequest.class));
        doNothing().when(volunteerService).addPointsAwarded(anyLong(), anyInt());

        // When & Then
        mockMvc.perform(post("/volunteer/submitComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"volunteerId\":1,\"eventId\":1,\"comment\":\"Great event!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("submitComment success"));
    }

    @Test
    public void testGetCredentialsByVolunteerId_Success() throws Exception {
        // Given
        Credential credential = new Credential();
        credential.setId(1L);
        List<Credential> credentialList = new ArrayList<>();
        credentialList.add(credential);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);

        when(volunteerService.getVolunteerByUserId(anyLong())).thenReturn(volunteer);
        when(volunteerService.getCredentialsByVolunteerId(anyLong())).thenReturn(credentialList);

        // When & Then
        mockMvc.perform(post("/volunteer/getCredentialsByVolunteerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"volunteerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }

    @Test
    public void testDeleteCredential_Success() throws Exception {
        // Given
        doNothing().when(volunteerService).deleteCredentialById(anyLong());

        // When & Then
        mockMvc.perform(post("/volunteer/deleteCredential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Credential deleted successfully."));
    }

    @Test
    public void testUpdateCredential_Success() throws Exception {
        // Given
        doNothing().when(volunteerService).updateCredential(any(Credential.class));

        // When & Then
        mockMvc.perform(post("/volunteer/updateCredential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"Updated Credential\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Credential updated successfully."));
    }

    @Test
    public void testGetVolunteerByUserId_Success() throws Exception {
        // Given
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        when(volunteerService.getVolunteerByUserId(anyLong())).thenReturn(volunteer);

        // When & Then
        mockMvc.perform(get("/volunteer/getVolunteerByUserId/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
