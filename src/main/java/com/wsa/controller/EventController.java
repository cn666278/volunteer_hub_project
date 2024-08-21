package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.*;
import com.wsa.service.EventService;
import com.wsa.service.OrganizerService;
import com.wsa.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private VolunteerService volunteerService;
    @GetMapping("/getEventList")
    public ResultVO<List<EventRes>> getAllEventsList() {

        List<Event> allEvents = eventService.getAllEvents();
        List<EventRes> eventResList = new ArrayList<>();
        for (Event e : allEvents) {
            EventRes eRes = new EventRes();
            eRes.setId(e.getId());
            eRes.setTitle(e.getTitle());
            eRes.setOrganizerId(e.getOrganizerId());
            eRes.setDescription(e.getDescription());
            eRes.setLocation(e.getLocation());
            eRes.setPointsAwarded(e.getPointsAwarded());
            eRes.setStartDate(e.getStartDate());
            eRes.setEndDate(e.getEndDate());
            eRes.setStatus(e.getStatus());
            eRes.setEventPic(e.getEventPic());
            eventResList.add(eRes);
        }

        if (allEvents != null) {
            return ResultVO.success(eventResList);
        } else {
            return ResultVO.failure("not found!");
        }
    }

    @GetMapping("/getEventsByDate")
    public ResultVO<List<EventRes>> getEventsByDate(@RequestParam("month") int month, @RequestParam("year") int year) {
        List<Event> eventsByMonth = eventService.getEventsByMonth(month, year);
        List<EventRes> eventRes = new ArrayList<>();
        for (Event e: eventsByMonth
        ) {
            EventRes eRes = new EventRes();
            eRes.setId(e.getId());
            eRes.setTitle(e.getTitle());
            eRes.setStartDate(e.getStartDate());
            eRes.setEndDate(e.getEndDate());
            eventRes.add(eRes);
        }
        if (eventsByMonth != null) {
            return ResultVO.success(eventRes);
        } else {
            return ResultVO.failure("not found!");
        }
    }

    @GetMapping("/getEventsByDateRange")
    public ResultVO<List<EventRes>> getEventsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Event> eventsByDateRange = eventService.getEventsByDateRange(startDate, endDate);
        List<EventRes> eventRes = new ArrayList<>();
        for (Event e: eventsByDateRange) {
            EventRes eRes = new EventRes();
            eRes.setId(e.getId());
            eRes.setTitle(e.getTitle());
            eRes.setStartDate(e.getStartDate());
            eRes.setEndDate(e.getEndDate());
            eventRes.add(eRes);
        }
        if (eventsByDateRange != null) {
            return ResultVO.success(eventRes);
        } else {
            return ResultVO.failure("not found!");
        }
    }

    @PostMapping("/registerEvent")
    public ResultVO<String> registerEvent(@RequestBody EventRequest eventRequest) {
        eventService.registerEvent(eventRequest);
        return ResultVO.success("success");
    }

    @PostMapping("/getEventById")
    public ResultVO<Event> getEventById(@RequestBody EventRequest eventRequest) {
        Event event = eventService.getEventById(eventRequest.getEventId());
        return ResultVO.success(event);
    }

    @PostMapping("/getEventsByOrganizerIdAndFilters")
    public ResultVO<PageInfo<EventRequest>> getEventsByOrganizerIdAndFilters(@RequestBody EventReqByOrganizerId eventRequest) {
        PageInfo<Event> events = eventService.getEventsByOrganizerIdAndFilters(eventRequest);
        PageInfo<EventRequest> eventRequestPageInfo = convertPageInfo(events);
        return ResultVO.success(eventRequestPageInfo);
    }

    public PageInfo<EventRequest> convertPageInfo(PageInfo<Event> eventsPage) {
        List<EventRequest> eventRequests = eventsPage.getList().stream()
                .map(event -> convertEventToEventRequest(event))
                .collect(Collectors.toList());

        PageInfo<EventRequest> eventRequestsPage = new PageInfo<>(eventRequests);
        eventRequestsPage.setPageNum(eventsPage.getPageNum());
        eventRequestsPage.setPageSize(eventsPage.getPageSize());
        eventRequestsPage.setTotal(eventsPage.getTotal());
        eventRequestsPage.setPages(eventsPage.getPages());
        eventRequestsPage.setSize(eventsPage.getSize());
        eventRequestsPage.setStartRow(eventsPage.getStartRow());
        eventRequestsPage.setEndRow(eventsPage.getEndRow());
        eventRequestsPage.setHasNextPage(eventsPage.isHasNextPage());
        eventRequestsPage.setHasPreviousPage(eventsPage.isHasPreviousPage());
        eventRequestsPage.setIsFirstPage(eventsPage.isIsFirstPage());
        eventRequestsPage.setIsLastPage(eventsPage.isIsLastPage());
        eventRequestsPage.setNavigatePages(eventsPage.getNavigatePages());
        eventRequestsPage.setNavigatepageNums(eventsPage.getNavigatepageNums());
        eventRequestsPage.setPrePage(eventsPage.getPrePage());
        eventRequestsPage.setNextPage(eventsPage.getNextPage());
        eventRequestsPage.setNavigateFirstPage(eventsPage.getNavigateFirstPage());
        eventRequestsPage.setNavigateLastPage(eventsPage.getNavigateLastPage());

        return eventRequestsPage;
    }

    private EventRequest convertEventToEventRequest(Event event) {
        return eventService.getEventDetailById(event.getId());
    }

    @PostMapping("/editEventById")
    public ResultVO<String> editEventById(@RequestBody EventRequest eventRequest) {
        eventService.editEventById(eventRequest);
        return ResultVO.success("success");
    }

    @PostMapping("/updateVolunteerStatus")
    public ResultVO<String> updateVolunteerStatus(@RequestBody UpdateStatusRequest request) {
        try {
            eventService.updateVolunteerStatus(request.getId(), request.getEmail(),request.getEventId(),request.getStatus());
            return ResultVO.success("Status updated successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to update status");
        }
    }

    @GetMapping("/getAllEvents")
    public ResultVO<List<EventRes>> getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            List<EventRes> eventResList = new ArrayList<>();
            for (Event e : events) {
                Organizer o = organizerService.getOrganizersById(e.getOrganizerId());
                EventRes eRes = new EventRes();
                eRes.setId(e.getId());
                eRes.setOrganizationName(o.getOrganizationName());
                eRes.setTitle(e.getTitle()); // Ensure the field name matches 'title'
                eRes.setStartDate(e.getStartDate()); // Ensure the field name matches 'startDate'
                eRes.setEndDate(e.getEndDate());
                eRes.setDescription(e.getDescription()); // Ensure the field name matches 'description'
                eRes.setId(e.getId()); // Ensure the field name matches 'organizer'
                eRes.setEventPic(e.getEventPic()); // Ensure the field name matches 'eventPic'
                eventResList.add(eRes);
            }
            return ResultVO.success(eventResList);
        } catch (Exception e) {
            return ResultVO.failure("Failed to fetch events");
        }
    }

    @GetMapping("/getLatestEvents")
    public ResultVO<List<EventRes>> getLatestEvents() {
        try {
            // 获取所有活动并按照 startDate 降序排序，限制结果数量为 3
            List<Event> latestEvents = eventService.getLatestEvents();

            // 转换为 EventRes 响应对象
            List<EventRes> eventResList = latestEvents.stream().map(event -> {
                Organizer o = organizerService.getOrganizersById(event.getOrganizerId());
                EventRes eRes = new EventRes();
                eRes.setId(event.getId());
                eRes.setTitle(event.getTitle());
                eRes.setOrganizerId(event.getOrganizerId());
                eRes.setDescription(event.getDescription());
                eRes.setLocation(event.getLocation());
                eRes.setPointsAwarded(event.getPointsAwarded());
                eRes.setStartDate(event.getStartDate());
                eRes.setEndDate(event.getEndDate());
                eRes.setStatus(event.getStatus());
                eRes.setEventPic(event.getEventPic());
                eRes.setOrganizationName(o.getOrganizationName());
                return eRes;
            }).collect(Collectors.toList());

            return ResultVO.success(eventResList);
        } catch (Exception e) {
            return ResultVO.failure("Failed to fetch latest events");
        }
    }



    @GetMapping("/{id}")
    public ResultVO<Event> getEventById(@PathVariable Long id) {
        try {
            Event event = eventService.getEventById(id);
            if (event != null) {
                return ResultVO.success(event);
            } else {
                return ResultVO.failure("Event not found");
            }
        } catch (Exception e) {
            return ResultVO.failure("Failed to fetch event details");
        }
    }

    @PostMapping("/subscribeForEvent")
    public ResultVO<String> subscribeForEvent(@RequestBody EventRegistrations eventRegistration) {
        try {
            Volunteer volunteer = volunteerService.getVolunteerByUserId(eventRegistration.getVolunteerId());
            eventRegistration.setVolunteerId(volunteer.getId());
            eventService.subscribeForEvent(eventRegistration);
            return ResultVO.success("Successfully subscribed to the event");
        } catch (IllegalStateException e) {
            return ResultVO.success("You have already subscribed to this event");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.success("Failed to subscribe to the event: " + e.getMessage());
        }
    }

    @PostMapping("/getSubscribedEvents")
    public ResultVO<List<EventRes>> getSubscribedEvents(@RequestBody EventRegistrations volunteerId) {
        try {
            List<Event> events = eventService.getSubscribedEventsByVolunteerId(volunteerId.getVolunteerId());
            List<EventRes> eventResList = new ArrayList<>();
            for (Event e : events) {
                Organizer organizer = organizerService.getOrganizersById(e.getOrganizerId());
                EventRes eRes = new EventRes();

                eRes.setId(e.getId());
                eRes.setTitle(e.getTitle());
                eRes.setOrganizerId(e.getOrganizerId());
                eRes.setOrganizationName(organizer.getOrganizationName());  // 设置 organizationName
                eRes.setDescription(e.getDescription());
                eRes.setLocation(e.getLocation());
                eRes.setPointsAwarded(e.getPointsAwarded());
                eRes.setStartDate(e.getStartDate());
                eRes.setEndDate(e.getEndDate());
                eRes.setStatus(e.getStatus());
                eRes.setEventPic(e.getEventPic());
                eventResList.add(eRes);
            }
            return ResultVO.success(eventResList);
        } catch (Exception e) {
            return ResultVO.failure("Failed to fetch subscribed events: " + e.getMessage());
        }
    }

    @PostMapping("/registerForEvent")
    public ResultVO<String> registerForEvent(@RequestBody EventRegistrations eventRegistration) {
        try {
            Volunteer volunteer = volunteerService.getVolunteerByUserId(eventRegistration.getVolunteerId());
            eventRegistration.setVolunteerId(volunteer.getId());
            eventService.registerForEvent(eventRegistration);
            return ResultVO.success("Application submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.success("Failed to submit application: " + e.getMessage());
        }
    }

    @PostMapping("/getParticipatedEvents")
    public ResultVO<List<EventRes>> getParticipatedEvents(@RequestBody EventRegistrations volunteerId) {
        try {
            Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId.getVolunteerId());
            List<Event> events = eventService.getParticipatedEventsByVolunteerId(volunteer.getId());
            List<EventRes> eventResList = new ArrayList<>();
            for (Event e : events) {
                Organizer organizer = organizerService.getOrganizersById(e.getOrganizerId());
                EventRes eRes = new EventRes();
                eRes.setId(e.getId());
                eRes.setTitle(e.getTitle());
                eRes.setOrganizerId(e.getOrganizerId());
                eRes.setOrganizationName(organizer.getOrganizationName());
                eRes.setDescription(e.getDescription());
                eRes.setLocation(e.getLocation());
                eRes.setPointsAwarded(e.getPointsAwarded());
                eRes.setStartDate(e.getStartDate());
                eRes.setEndDate(e.getEndDate());
                eRes.setStatus(e.getStatus());
                eRes.setEventPic(e.getEventPic());
                eventResList.add(eRes);
            }
            return ResultVO.success(eventResList);
        } catch (Exception e) {
            return ResultVO.failure("Failed to fetch participated events: " + e.getMessage());
        }
    }



    // 获取事件统计数据的接口
    @GetMapping("/getEventStats")
    public ResultVO<List<EventDataRes>> getEventStats() {
        List<EventDataRes> stats = eventService.getEventStats();
        return ResultVO.success(stats);
    }

    @PostMapping("/approveEvent")
    public ResultVO<String> approveEvent(@RequestBody EventUpdateRes id) {
        try {
            // 调用service方法将事件状态更新为 "Passed"
            eventService.updateEventStatusToPassed(id.getId());
            return ResultVO.success("Event approved successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to approve event: " + e.getMessage());
        }
    }

    @PostMapping("/rejectEvent")
    public ResultVO<String> rejectEvent(@RequestBody EventUpdateRes id) {
        try {
            // 调用service方法将事件状态更新为 "Passed"
            eventService.updateEventStatusToRejected(id.getId());
            return ResultVO.success("Event rejected successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to reject event: " + e.getMessage());
        }
    }

}
