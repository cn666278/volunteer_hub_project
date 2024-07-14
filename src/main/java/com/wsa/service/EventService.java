package com.wsa.service;

import com.wsa.model.EventRequest;
import com.wsa.mapper.EventFacilitiesMapper;
import com.wsa.mapper.EventRolesMapper;
import com.wsa.model.Event;
import com.wsa.mapper.EventMapper;
import com.wsa.model.EventFacilities;
import com.wsa.model.EventRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventRolesMapper eventRolesMapper;

    @Autowired
    private EventFacilitiesMapper eventFacilitiesMapper;
    public List<Event> getEventsByMonth(int month, int year) {
        return eventMapper.findEventsByMonth(month, year);
    }

    public void registerEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setOrganizerId(eventRequest.getOrganizerId());
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setPointsAwarded(eventRequest.getPointsAwarded());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());

        eventMapper.saveEvent(event);
        Long eventId = event.getId();

        for (EventRequest.RoleQuantity roleQuantity : eventRequest.getRoles()) {
            EventRoles eventRoles = new EventRoles();
            eventRoles.setEventId(eventId);
            eventRoles.setRoleName(roleQuantity.getRole());
            eventRoles.setVolunteerCount(roleQuantity.getQuantity());
            eventRolesMapper.saveEventRoles(eventRoles);
        }

        for (String facility : eventRequest.getNearbyFacilities()) {
            EventFacilities eventFacilities = new EventFacilities();
            eventFacilities.setEventId(eventId);
            eventFacilities.setName(facility);
            eventFacilitiesMapper.saveEventFacilities(eventFacilities);
        }
    }
}