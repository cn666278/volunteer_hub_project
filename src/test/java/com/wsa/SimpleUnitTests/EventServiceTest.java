package com.wsa.SimpleUnitTests;

import com.wsa.mapper.*;
import com.wsa.model.*;
import com.wsa.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventRolesMapper eventRolesMapper;

    @Mock
    private EventFacilitiesMapper eventFacilitiesMapper;

    @Mock
    private EventRegistrationsMapper eventRegistrationsMapper;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllEvents() {
        // Arrange
        Event event1 = new Event();
        Event event2 = new Event();
        when(eventMapper.findAllEvents()).thenReturn(Arrays.asList(event1, event2));

        // Act
        List<Event> events = eventService.getAllEvents();

        // Assert
        assertThat(events).hasSize(2);
        verify(eventMapper, times(1)).findAllEvents();
    }

    @Test
    void testGetEventById() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        when(eventMapper.getEventById(eventId)).thenReturn(event);

        // Act
        Event result = eventService.getEventById(eventId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(eventId);
        verify(eventMapper, times(1)).getEventById(eventId);
    }

    @Test
    void testHasAlreadySubscribed() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        when(eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId)).thenReturn(1);

        // Act
        boolean result = eventService.hasAlreadySubscribed(eventId, volunteerId);

        // Assert
        assertThat(result).isTrue();
        verify(eventRegistrationsMapper, times(1)).countByEventIdAndVolunteerId(eventId, volunteerId);
    }

    @Test
    void testHasAlreadyRegistered() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        when(eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId)).thenReturn(1);

        // Act
        boolean result = eventService.hasAlreadyRegistered(eventId, volunteerId);

        // Assert
        assertThat(result).isTrue();
        verify(eventRegistrationsMapper, times(1)).countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId);
    }

    @Test
    void testSubscribeForEventAlreadySubscribed() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        EventRegistrations registration = new EventRegistrations();
        registration.setEventId(eventId);
        registration.setVolunteerId(volunteerId);

        when(eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId)).thenReturn(1);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            eventService.subscribeForEvent(registration);
        });
    }

    @Test
    void testSubscribeForEventNewSubscription() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        EventRegistrations registration = new EventRegistrations();
        registration.setEventId(eventId);
        registration.setVolunteerId(volunteerId);

        when(eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId)).thenReturn(0);

        // Act
        eventService.subscribeForEvent(registration);

        // Assert
        verify(eventRegistrationsMapper, times(1)).saveEventRegistration(registration);
    }

    @Test
    void testRegisterForEventAlreadyRegistered() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        EventRegistrations registration = new EventRegistrations();
        registration.setEventId(eventId);
        registration.setVolunteerId(volunteerId);

        when(eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId)).thenReturn(1);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            eventService.registerForEvent(registration);
        });
    }

    @Test
    void testRegisterForEventNewRegistration() {
        // Arrange
        Long eventId = 1L;
        Long volunteerId = 1L;
        EventRegistrations registration = new EventRegistrations();
        registration.setEventId(eventId);
        registration.setVolunteerId(volunteerId);

        when(eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId)).thenReturn(0);

        // Act
        eventService.registerForEvent(registration);

        // Assert
        verify(eventRegistrationsMapper, times(1)).saveEventRegistration(registration);
    }

    @Test
    void testGetSubscribedEventsByVolunteerId() {
        // Arrange
        Long volunteerId = 1L;
        EventRegistrations registration1 = new EventRegistrations();
        registration1.setEventId(1L);
        EventRegistrations registration2 = new EventRegistrations();
        registration2.setEventId(2L);

        Event event1 = new Event();
        Event event2 = new Event();

        when(eventRegistrationsMapper.findByVolunteerIdAndStatus(volunteerId, "subscribed"))
                .thenReturn(Arrays.asList(registration1, registration2));
        when(eventMapper.getEventById(1L)).thenReturn(event1);
        when(eventMapper.getEventById(2L)).thenReturn(event2);

        // Act
        List<Event> events = eventService.getSubscribedEventsByVolunteerId(volunteerId);

        // Assert
        assertThat(events).hasSize(2);
        verify(eventRegistrationsMapper, times(1)).findByVolunteerIdAndStatus(volunteerId, "subscribed");
        verify(eventMapper, times(1)).getEventById(1L);
        verify(eventMapper, times(1)).getEventById(2L);
    }

    @Test
    void testGetEventStats() {
        // Arrange
        Event event1 = new Event();
        event1.setId(1L);
        event1.setTitle("Event 1");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Event 2");

        when(eventMapper.findAllEvents()).thenReturn(Arrays.asList(event1, event2));
        when(eventRegistrationsMapper.getTodayActiveUsers(1L)).thenReturn(10);
        when(eventRegistrationsMapper.getTodayActiveUsers(2L)).thenReturn(20);
        when(eventRegistrationsMapper.getMonthlyActiveUsers(1L)).thenReturn(30);
        when(eventRegistrationsMapper.getMonthlyActiveUsers(2L)).thenReturn(40);
        when(eventRegistrationsMapper.getTotalUsers(1L)).thenReturn(50);
        when(eventRegistrationsMapper.getTotalUsers(2L)).thenReturn(60);

        // Act
        List<EventDataRes> stats = eventService.getEventStats();

        // Assert
        assertThat(stats).hasSize(2);
        assertThat(stats.get(0).getName()).isEqualTo("Event 1");
        assertThat(stats.get(0).getToday()).isEqualTo(10);
        assertThat(stats.get(0).getMonthly()).isEqualTo(30);
        assertThat(stats.get(0).getTotal()).isEqualTo(50);
        verify(eventMapper, times(1)).findAllEvents();
        verify(eventRegistrationsMapper, times(1)).getTodayActiveUsers(1L);
        verify(eventRegistrationsMapper, times(1)).getMonthlyActiveUsers(1L);
        verify(eventRegistrationsMapper, times(1)).getTotalUsers(1L);
    }
}
