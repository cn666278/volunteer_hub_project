package com.wsa.itemmockmvctests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.wsa.model.*;
import com.wsa.service.EventService;
import com.wsa.service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    @MockBean
    private EventService eventService;

    @Test
    public void shouldReturnVolunteersByEventId() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setUserId(1L);
        volunteer.setRoleId(1L);
        volunteer.setRoleName("Event Coordinator");

        PageInfo<Volunteer> pageInfo = new PageInfo<>(Arrays.asList(volunteer));
        when(volunteerService.getVolunteersByEventId(1L, "Active", 1, 10)).thenReturn(pageInfo);

        mockMvc.perform(get("/volunteer/getVolunteersByEventId")
                        .param("eventId", "1")
                        .param("status", "Active")
                        .param("pageNum", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.list", hasSize(1)))
                .andExpect(jsonPath("$.data.list[0].roleName").value("Event Coordinator"));
    }

    @Test
    public void shouldSubmitComment() throws Exception {
        SubmitCommentRequest request = new SubmitCommentRequest();
        request.setEventId(1L);
        request.setVolunteerId(1L);
        request.setComment("Great event!");

        when(eventService.getEventById(1L)).thenReturn(new Event());
        mockMvc.perform(post("/volunteer/submitComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("submitComment success"));
    }

    @Test
    public void shouldReturnCredentialsByVolunteerId() throws Exception {
        Credential credential = new Credential();
        credential.setCredentialName("First Aid Certification");
        credential.setCredentialUrl("http://example.com/certification");

        when(volunteerService.getCredentialsByVolunteerId(1L)).thenReturn(Arrays.asList(credential));

        Credential credentialRequest = new Credential();
        credentialRequest.setVolunteerId(1L);

        mockMvc.perform(post("/volunteer/getCredentialsByVolunteerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentialRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].credentialName").value("First Aid Certification"));
    }

    @Test
    public void shouldDeleteCredential() throws Exception {
        Credential credential = new Credential();
        credential.setId(1L);

        mockMvc.perform(post("/volunteer/deleteCredential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credential)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Credential deleted successfully."));
    }

    @Test
    public void shouldUpdateCredential() throws Exception {
        Credential credential = new Credential();
        credential.setId(1L);
        credential.setCredentialName("Updated Certification");
        credential.setCredentialUrl("http://example.com/updated-certification");

        mockMvc.perform(post("/volunteer/updateCredential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credential)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Credential updated successfully."));
    }

    @Test
    public void shouldReturnVolunteerByUserId() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setUserId(1L);
        volunteer.setExperience("5 years of event management");

        when(volunteerService.getVolunteerByUserId(1L)).thenReturn(volunteer);

        mockMvc.perform(get("/volunteer/getVolunteerByUserId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.experience").value("5 years of event management"));
    }
}
