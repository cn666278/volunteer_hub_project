package com.wsa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsa.mapper.EventRegistrationsMapper;
import com.wsa.model.*;
import com.wsa.mapper.EventFacilitiesMapper;
import com.wsa.mapper.EventRolesMapper;
import com.wsa.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventRolesMapper eventRolesMapper;

    @Autowired
    private EventFacilitiesMapper eventFacilitiesMapper;

    @Autowired
    private EventRegistrationsMapper eventRegistrationsMapper;

    // Method to get event statistics
    public List<EventDataRes> getEventStats() {
        // Retrieve all events
        List<Event> events = eventMapper.findAllEvents();
        List<EventDataRes> eventDataResList = new ArrayList<>();

        // Iterate through events to collect statistics
        for (Event event : events) {
            EventDataRes eventDataRes = new EventDataRes();
            eventDataRes.setName(event.getTitle());

            // Get today's active users count
            Integer todayActiveUsers = eventRegistrationsMapper.getTodayActiveUsers(event.getId());
            eventDataRes.setToday(todayActiveUsers);

            // Get this month's active users count
            Integer monthlyActiveUsers = eventRegistrationsMapper.getMonthlyActiveUsers(event.getId());
            eventDataRes.setMonthly(monthlyActiveUsers);

            // Get total users count
            Integer totalUsers = eventRegistrationsMapper.getTotalUsers(event.getId());
            eventDataRes.setTotal(totalUsers);

            eventDataResList.add(eventDataRes);
        }

        return eventDataResList;
    }

    // Method to retrieve events by page
    public List<Event> getEventsByPage(int current, int pageSize) {
        int offset = (current - 1) * pageSize;
        return eventMapper.getEventsByPage(offset, pageSize);
    }

    // Method to get total number of events
    public int getTotalEventsCount() {
        return eventMapper.getTotalEventsCount();
    }

    // Method to retrieve events within a date range
    public List<Event> getEventsByDateRange(Date startDate, Date endDate) {
        return eventMapper.findEventsByDateRange(startDate, endDate);
    }

    // Method to retrieve events by month and year
    public List<Event> getEventsByMonth(int month, int year) {
        return eventMapper.findEventsByMonth(month, year);
    }

    // Method to register a new event
    public void registerEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setOrganizerId(eventRequest.getOrganizerId());
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setPointsAwarded(eventRequest.getPointsAwarded());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setEventPic(String.valueOf(eventRequest.getFileIds().get(0)));
        eventMapper.saveEvent(event);
        Long eventId = event.getId();

        // Save event roles
        for (EventRequest.RoleQuantity roleQuantity : eventRequest.getRoles()) {
            EventRoles eventRoles = new EventRoles();
            eventRoles.setEventId(eventId);
            eventRoles.setRoleName(roleQuantity.getRole());
            eventRoles.setVolunteerCount(roleQuantity.getQuantity());
            eventRolesMapper.saveEventRoles(eventRoles);
        }

        // Save event facilities
        for (String facility : eventRequest.getNearbyFacilities()) {
            EventFacilities eventFacilities = new EventFacilities();
            eventFacilities.setEventId(eventId);
            eventFacilities.setName(facility);
            eventFacilitiesMapper.saveEventFacilities(eventFacilities);
        }
    }

    // Method to get events by organizer ID with filters and pagination
    public PageInfo<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest) {
        PageHelper.startPage(eventRequest.getPage(), eventRequest.getSize());
        List<Event> events = eventMapper.getEventsByOrganizerIdAndFilters(eventRequest);
        return new PageInfo<>(events);
    }

    // Method to get event details by ID
    public EventRequest getEventDetailById(Long id) {
        return eventMapper.getEventDetailById(id);
    }

    // Method to edit an existing event by ID
    public void editEventById(EventRequest eventRequest) {
        Event event = new Event();
        Long id = eventRequest.getEventId();
        event.setId(id);
        event.setOrganizerId(eventRequest.getOrganizerId());
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setPointsAwarded(eventRequest.getPointsAwarded());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setEventPic(String.valueOf(eventRequest.getFileIds().get(0)));
        eventMapper.editEventById(event);

        // Update event roles
        eventRolesMapper.deleteEventRolesByEventId(id);
        for (EventRequest.RoleQuantity roleQuantity : eventRequest.getRoles()) {
            EventRoles eventRoles = new EventRoles();
            eventRoles.setEventId(id);
            eventRoles.setRoleName(roleQuantity.getRole());
            eventRoles.setVolunteerCount(roleQuantity.getQuantity());
            eventRolesMapper.saveEventRoles(eventRoles);
        }

        // Update event facilities
        eventFacilitiesMapper.deleteEventFacilitiesByEventId(id);
        for (String facility : eventRequest.getNearbyFacilities()) {
            EventFacilities eventFacilities = new EventFacilities();
            eventFacilities.setEventId(id);
            eventFacilities.setName(facility);
            eventFacilitiesMapper.saveEventFacilities(eventFacilities);
        }
    }

    // Method to update volunteer status for an event
    public void updateVolunteerStatus(Long id, String email, Long eventId, String status) {
        eventRegistrationsMapper.updateStatusById(id, status);
    }

    // Method to retrieve all events
    public List<Event> getAllEvents() {
        return eventMapper.findAllEvents();
    }

    // Method to get event details by ID
    public Event getEventById(Long id) {
        return eventMapper.getEventById(id);
    }

    // Method to check if a volunteer has already subscribed to an event
    public boolean hasAlreadySubscribed(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId) > 0;
    }

    // Method to check if a volunteer has already registered for an event
    public boolean hasAlreadyRegistered(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId) > 0;
    }

    // Method to subscribe for an event
    public void subscribeForEvent(EventRegistrations eventRegistration) {
        if (!hasAlreadySubscribed(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already subscribed to this event");
        }
    }

    // Method to retrieve subscribed events by volunteer ID
    public List<Event> getSubscribedEventsByVolunteerId(Long volunteerId) {
        List<EventRegistrations> registrations = eventRegistrationsMapper.findByVolunteerIdAndStatus(volunteerId, "subscribed");
        List<Event> subscribedEvents = new ArrayList<>();

        // Retrieve each event by its ID
        for (EventRegistrations registration : registrations) {
            Event event = eventMapper.getEventById(registration.getEventId());
            if (event != null) {
                subscribedEvents.add(event);
            }
        }

        return subscribedEvents;
    }

    // Method to register for an event
    public void registerForEvent(EventRegistrations eventRegistration) {
        // Check if already registered
        if (!hasAlreadyRegistered(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already registered for this event");
        }
    }

    // Method to retrieve participated events by volunteer ID
    public List<Event> getParticipatedEventsByVolunteerId(Long volunteerId) {
        return eventMapper.findParticipatedEventsByVolunteerId(volunteerId);
    }

    // Method to retrieve the latest events
    public List<Event> getLatestEvents() {
        return eventMapper.findLatestEvents();
    }

    // Method to retrieve roles by event ID
    public List<EventRoles> getRolesByEventId(Long eventId) {
        return eventRolesMapper.findRolesByEventId(eventId);
    }

    // Method to update event status to "Passed"
    public void updateEventStatusToPassed(Long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if (event != null) {
            event.setStatus("Passed");
            eventMapper.approveEvent(event); // Update event
        }
    }

    // Method to update event status to "Rejected"
    public void updateEventStatusToRejected(Long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if (event != null) {
            event.setStatus("Rejected");
            eventMapper.approveEvent(event); // Update event
        }
    }
}
