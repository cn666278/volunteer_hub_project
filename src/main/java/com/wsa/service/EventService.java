package com.wsa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsa.mapper.EventRegistrationsMapper;
import com.wsa.model.*;
import com.wsa.mapper.EventFacilitiesMapper;
import com.wsa.mapper.EventRolesMapper;
import com.wsa.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper; // Mapper for database interactions related to events

    @Autowired
    private EventRolesMapper eventRolesMapper; // Mapper for event roles management

    @Autowired
    private EventFacilitiesMapper eventFacilitiesMapper; // Mapper for event facilities management

    @Autowired
    private EventRegistrationsMapper eventRegistrationsMapper; // Mapper for event registrations management

    // Other methods omitted...

    /**
     * Method to retrieve event statistics.
     * @return List of EventDataRes containing statistics for each event.
     */
    public List<EventDataRes> getEventStats() {
        // Retrieve all events
        List<Event> events = eventMapper.findAllEvents();
        List<EventDataRes> eventDataResList = new ArrayList<>();

        // Iterate through each event to calculate statistics
        for (Event event : events) {
            EventDataRes eventDataRes = new EventDataRes();
            eventDataRes.setName(event.getTitle());

            // Retrieve today's active users for the event
            Integer todayActiveUsers = eventRegistrationsMapper.getTodayActiveUsers(event.getId());
            eventDataRes.setToday(todayActiveUsers);

            // Retrieve this month's active users for the event
            Integer monthlyActiveUsers = eventRegistrationsMapper.getMonthlyActiveUsers(event.getId());
            eventDataRes.setMonthly(monthlyActiveUsers);

            // Retrieve total users for the event
            Integer totalUsers = eventRegistrationsMapper.getTotalUsers(event.getId());
            eventDataRes.setTotal(totalUsers);

            eventDataResList.add(eventDataRes);
        }

        return eventDataResList;
    }

    /**
     * Method to retrieve events by pagination.
     * @param current The current page number.
     * @param pageSize The number of records per page.
     * @return List of events for the specified page.
     */
    public List<Event> getEventsByPage(int current, int pageSize) {
        int offset = (current - 1) * pageSize;
        return eventMapper.getEventsByPage(offset, pageSize);
    }

    /**
     * Method to get the total count of events.
     * @return The total number of events.
     */
    public int getTotalEventsCount() {
        return eventMapper.getTotalEventsCount();
    }

    /**
     * Method to retrieve events within a specific date range.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return List of events within the specified date range.
     */
    public List<Event> getEventsByDateRange(Date startDate, Date endDate) {
        return eventMapper.findEventsByDateRange(startDate, endDate);
    }

    /**
     * Method to retrieve events for a specific month and year.
     * @param month The month to filter events.
     * @param year The year to filter events.
     * @return List of events occurring in the specified month and year.
     */
    public List<Event> getEventsByMonth(int month, int year) {
        return eventMapper.findEventsByMonth(month, year);
    }

    /**
     * Method to register a new event.
     * @param eventRequest The event request containing event details.
     */
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

    /**
     * Method to retrieve events filtered by organizer ID with pagination.
     * @param eventRequest The request containing organizer ID and pagination details.
     * @return Paginated list of events.
     */
    public PageInfo<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest) {
        PageHelper.startPage(eventRequest.getPage(), eventRequest.getSize());
        List<Event> events = eventMapper.getEventsByOrganizerIdAndFilters(eventRequest);
        return new PageInfo<>(events);
    }

    /**
     * Method to retrieve detailed information of an event by its ID.
     * @param id The ID of the event.
     * @return EventRequest containing detailed information of the event.
     */
    public EventRequest getEventDetailById(Long id) {
        return eventMapper.getEventDetailById(id);
    }

    /**
     * Method to edit an existing event by its ID.
     * @param eventRequest The request containing updated event details.
     */
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

    /**
     * Method to update volunteer status for an event.
     * @param id The ID of the registration.
     * @param email The email of the volunteer.
     * @param eventId The ID of the event.
     * @param status The new status to update.
     */
    public void updateVolunteerStatus(Long id, String email, Long eventId, String status) {
        eventRegistrationsMapper.updateStatusById(id, status);
    }

    /**
     * Method to retrieve all events.
     * @return List of all events.
     */
    public List<Event> getAllEvents() {
        return eventMapper.findAllEvents();
    }

    /**
     * Method to retrieve an event by its ID.
     * @param id The ID of the event.
     * @return The event object.
     */
    public Event getEventById(Long id) {
        return eventMapper.getEventById(id);
    }

    /**
     * Check if a volunteer has already subscribed to an event.
     * @param eventId The ID of the event.
     * @param volunteerId The ID of the volunteer.
     * @return True if the volunteer has already subscribed, false otherwise.
     */
    public boolean hasAlreadySubscribed(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId) > 0;
    }

    /**
     * Check if a volunteer has already registered for an event.
     * @param eventId The ID of the event.
     * @param volunteerId The ID of the volunteer.
     * @return True if the volunteer has already registered, false otherwise.
     */
    public boolean hasAlreadyRegistered(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId) > 0;
    }

    /**
     * Method to subscribe a volunteer to an event.
     * @param eventRegistration The event registration details.
     */
    public void subscribeForEvent(EventRegistrations eventRegistration) {
        if (!hasAlreadySubscribed(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already subscribed to this event");
        }
    }

    /**
     * Method to retrieve subscribed events for a volunteer.
     * @param volunteerId The ID of the volunteer.
     * @return List of subscribed events.
     */
    public List<Event> getSubscribedEventsByVolunteerId(Long volunteerId) {
        List<EventRegistrations> registrations = eventRegistrationsMapper.findByVolunteerIdAndStatus(volunteerId, "subscribed");
        List<Event> subscribedEvents = new ArrayList<>();

        for (EventRegistrations registration : registrations) {
            Event event = eventMapper.getEventById(registration.getEventId());
            if (event != null) {
                subscribedEvents.add(event);
            }
        }

        return subscribedEvents;
    }

    /**
     * Method to register a volunteer for an event.
     * @param eventRegistration The event registration details.
     */
    public void registerForEvent(EventRegistrations eventRegistration) {
        // Check if already registered
        if (!hasAlreadyRegistered(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            // Insert registration record
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already registered for this event");
        }
    }

    /**
     * Method to retrieve events in which a volunteer has participated.
     * @param volunteerId The ID of the volunteer.
     * @return List of participated events.
     */
    public List<Event> getParticipatedEventsByVolunteerId(Long volunteerId) {
        return eventMapper.findParticipatedEventsByVolunteerId(volunteerId);
    }

    /**
     * Method to retrieve the latest events.
     * @return List of the latest events.
     */
    public List<Event> getLatestEvents() {
        return eventMapper.findLatestEvents();
    }

    /**
     * Method to retrieve roles for a specific event.
     * @param eventId The ID of the event.
     * @return List of roles associated with the event.
     */
    public List<EventRoles> getRolesByEventId(Long eventId) {
        return eventRolesMapper.findRolesByEventId(eventId);
    }

    /**
     * Method to update the status of an event to "Passed".
     * @param eventId The ID of the event.
     */
    public void updateEventStatusToPassed(Long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if (event != null) {
            event.setStatus("Passed");
            eventMapper.approveEvent(event); // Update the event status to "Passed"
        }
    }

    /**
     * Method to update the status of an event to "Rejected".
     * @param eventId The ID of the event.
     */
    public void updateEventStatusToRejected(Long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if (event != null) {
            event.setStatus("Rejected");
            eventMapper.approveEvent(event); // Update the event status to "Rejected"
        }
    }
}
