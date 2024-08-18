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

    // 其他方法省略...

    // 新增获取事件统计数据的方法
    public List<EventDataRes> getEventStats() {
        // 获取所有事件列表
        List<Event> events = eventMapper.findAllEvents();
        List<EventDataRes> eventDataResList = new ArrayList<>();

        for (Event event : events) {
            EventDataRes eventDataRes = new EventDataRes();
            eventDataRes.setName(event.getTitle());

            // 获取今日活跃用户数
            Integer todayActiveUsers = eventRegistrationsMapper.getTodayActiveUsers(event.getId());
            eventDataRes.setToday(todayActiveUsers);

            // 获取本月活跃用户数
            Integer monthlyActiveUsers = eventRegistrationsMapper.getMonthlyActiveUsers(event.getId());
            eventDataRes.setMonthly(monthlyActiveUsers);

            // 获取总用户数
            Integer totalUsers = eventRegistrationsMapper.getTotalUsers(event.getId());
            eventDataRes.setTotal(totalUsers);

            eventDataResList.add(eventDataRes);
        }

        return eventDataResList;
    }

    public List<Event> getEventsByPage(int current, int pageSize) {
        int offset = (current - 1) * pageSize;
        return eventMapper.getEventsByPage(offset, pageSize);
    }

    public int getTotalEventsCount() {
        return eventMapper.getTotalEventsCount();
    }

    public List<Event> getEventsByDateRange(Date startDate, Date endDate) {
        return eventMapper.findEventsByDateRange(startDate, endDate);
    }

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
        event.setEventPic(String.valueOf(eventRequest.getFileIds().get(0)));
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
        event.setEventPic(String.valueOf(eventRequest.getFileIds().get(1)));
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

    public void updateVolunteerStatus(Long id, String email,Long eventId,String status) {
        eventRegistrationsMapper.updateStatusById(id, status);
    }

    public List<Event> getAllEvents() {
        return eventMapper.findAllEvents();
    }

    public Event getEventById(Long id) {
        return eventMapper.getEventById(id);
    }

    public boolean hasAlreadySubscribed(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerId(eventId, volunteerId) > 0;
    }
    public boolean hasAlreadyRegistered(Long eventId, Long volunteerId) {
        return eventRegistrationsMapper.countByEventIdAndVolunteerIdForRegistered(eventId, volunteerId) > 0;
    }
    public void subscribeForEvent(EventRegistrations eventRegistration) {
        if (!hasAlreadySubscribed(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already subscribed to this event");
        }
    }

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

    public void registerForEvent(EventRegistrations eventRegistration) {
        // 检查是否已经注册
        if (!hasAlreadyRegistered(eventRegistration.getEventId(), eventRegistration.getVolunteerId())) {
            // 插入注册记录
            eventRegistrationsMapper.saveEventRegistration(eventRegistration);
        } else {
            throw new IllegalStateException("User has already registered for this event");
        }
    }

    public List<Event> getParticipatedEventsByVolunteerId(Long volunteerId) {
        return eventMapper.findParticipatedEventsByVolunteerId(volunteerId);
    }


}
