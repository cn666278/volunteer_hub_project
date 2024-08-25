package com.wsa.itemmockmvctests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsa.model.Event;
import com.wsa.model.EventRequest;
import com.wsa.model.UpdateStatusRequest;
import com.wsa.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void shouldReturnAllEvents() throws Exception {
        // 使用默认构造函数创建 Event 对象，并通过 setter 方法设置属性
        Event event1 = new Event();
        event1.setId(1L);
        event1.setOrganizerId(1L);
        event1.setTitle("Event 1");
        event1.setDescription("Description 1");
        event1.setLocation("Location 1");
        event1.setStartDate(new Timestamp(System.currentTimeMillis()));
        event1.setEndDate(new Timestamp(System.currentTimeMillis()));
        event1.setStatus("Active");
        event1.setPointsAwarded(10);
        event1.setEventPic("pic1.png");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setOrganizerId(2L);
        event2.setTitle("Event 2");
        event2.setDescription("Description 2");
        event2.setLocation("Location 2");
        event2.setStartDate(new Timestamp(System.currentTimeMillis()));
        event2.setEndDate(new Timestamp(System.currentTimeMillis()));
        event2.setStatus("Active");
        event2.setPointsAwarded(20);
        event2.setEventPic("pic2.png");

        List<Event> events = Arrays.asList(event1, event2);

        when(eventService.getAllEvents()).thenReturn(events);

        // 执行测试
        mockMvc.perform(get("/event/getAllEvents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title").value("Event 1"))
                .andExpect(jsonPath("$.data[1].title").value("Event 2"));
    }

    @Test
    public void shouldReturnEventsByDate() throws Exception {
        // 准备测试数据
        Event event = new Event();
        event.setId(1L);
        event.setOrganizerId(1L);
        event.setTitle("Event 1");
        event.setDescription("Description 1");
        event.setLocation("Location 1");
        event.setStartDate(new Timestamp(System.currentTimeMillis()));
        event.setEndDate(new Timestamp(System.currentTimeMillis()));
        event.setStatus("Active");
        event.setPointsAwarded(10);
        event.setEventPic("pic1.png");

        List<Event> events = Arrays.asList(event);

        when(eventService.getEventsByMonth(8, 2024)).thenReturn(events);

        // 执行测试
        mockMvc.perform(get("/event/getEventsByDate")
                        .param("month", "8")
                        .param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].title").value("Event 1"));
    }

    @Test
    public void shouldRegisterEvent() throws Exception {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setOrganizerId(1L);
        eventRequest.setTitle("New Event");
        eventRequest.setDescription("New Event Description");
        eventRequest.setLocation("Location");
        eventRequest.setPointsAwarded(50);
        eventRequest.setStartDate(new Timestamp(System.currentTimeMillis()));
        eventRequest.setEndDate(new Timestamp(System.currentTimeMillis()));
        eventRequest.setFileIds(Collections.singletonList(1L));

        // 执行测试
        mockMvc.perform(post("/event/registerEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    public void shouldReturnEventById() throws Exception {
        // 准备测试数据
        Event event = new Event();
        event.setId(1L);
        event.setOrganizerId(1L);
        event.setTitle("Event 1");
        event.setDescription("Description 1");
        event.setLocation("Location 1");
        event.setStartDate(new Timestamp(System.currentTimeMillis()));
        event.setEndDate(new Timestamp(System.currentTimeMillis()));
        event.setStatus("Active");
        event.setPointsAwarded(10);
        event.setEventPic("pic1.png");

        when(eventService.getEventById(1L)).thenReturn(event);

        // 执行测试
        mockMvc.perform(post("/event/getEventById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Event 1"));
    }

    @Test
    public void shouldUpdateVolunteerStatus() throws Exception {
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId(1L);
        updateStatusRequest.setEmail("volunteer@example.com");
        updateStatusRequest.setEventId(1L);
        updateStatusRequest.setStatus("Approved");

        // 执行测试
        mockMvc.perform(post("/event/updateVolunteerStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Status updated successfully"));
    }
}
