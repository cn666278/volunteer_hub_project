package com.wsa.lightweightmockmvctests;

import com.github.pagehelper.PageInfo;
import com.wsa.controller.EventController;
import com.wsa.model.*;
import com.wsa.service.EventService;
import com.wsa.service.OrganizerService;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private OrganizerService organizerService;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetAllEventsList_Success() throws Exception {
        // Given
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setOrganizerId(1L);
        event.setDescription("Test Description");
        events.add(event);

        when(eventService.getAllEvents()).thenReturn(events);

        // When & Then
        mockMvc.perform(get("/event/getEventList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("Test Event"));
    }

    @Test
    public void testGetEventsByDate_Success() throws Exception {
        // Given
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        events.add(event);

        when(eventService.getEventsByMonth(8, 2024)).thenReturn(events);

        // When & Then
        mockMvc.perform(get("/event/getEventsByDate")
                        .param("month", "8")
                        .param("year", "2024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data[0].title").value("Test Event"));
    }

    @Test
    public void testGetEventsByDateRange_Success() throws Exception {
        // Given
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        events.add(event);

        Date startDate = new Date(); // Set appropriate startDate
        Date endDate = new Date();   // Set appropriate endDate

        when(eventService.getEventsByDateRange(startDate, endDate)).thenReturn(events);

        // When & Then
        mockMvc.perform(get("/event/getEventsByDateRange")
                        .param("startDate", "2024-08-01")
                        .param("endDate", "2024-08-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data[0].title").value("Test Event"));
    }

    @Test
    public void testRegisterEvent_Success() throws Exception {
        // Given
        EventRequest eventRequest = new EventRequest();
        eventRequest.setTitle("New Event");

        // When & Then
        mockMvc.perform(post("/event/registerEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    public void testGetEventById_Success() throws Exception {
        // Given
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Event 1");

        when(eventService.getEventById(1L)).thenReturn(event);

        // When & Then
        mockMvc.perform(post("/event/getEventById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.title").value("Event 1"));
    }
}
