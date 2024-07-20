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

    public PageInfo<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest) {
        PageHelper.startPage(eventRequest.getPage(), eventRequest.getSize());
        List<Event> events = eventMapper.getEventsByOrganizerIdAndFilters(eventRequest);
        return new PageInfo<>(events);
    }

    public EventRequest getEventDetailById(Long id) {
        return eventMapper.getEventDetailById(id);
    }

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
        eventMapper.editEventById(event);

        eventRolesMapper.deleteEventRolesByEventId(id);
        for (EventRequest.RoleQuantity roleQuantity : eventRequest.getRoles()) {
            EventRoles eventRoles = new EventRoles();
            eventRoles.setEventId(id);
            eventRoles.setRoleName(roleQuantity.getRole());
            eventRoles.setVolunteerCount(roleQuantity.getQuantity());
            eventRolesMapper.saveEventRoles(eventRoles);
        }

        eventFacilitiesMapper.deleteEventFacilitiesByEventId(id);
        for (String facility : eventRequest.getNearbyFacilities()) {
            EventFacilities eventFacilities = new EventFacilities();
            eventFacilities.setEventId(id);
            eventFacilities.setName(facility);
            eventFacilitiesMapper.saveEventFacilities(eventFacilities);
        }
    }

    public void updateVolunteerStatus(Long id, String status) {
        eventRegistrationsMapper.updateStatusById(id, status);
    }
}