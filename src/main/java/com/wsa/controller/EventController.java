package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.*;
import com.wsa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/getEventList")
    public ResultVO<List<EventRes>> getAllEvents() {
        List<Event> allEvents = eventService.getAllEvents();
        List<EventRes> eventRes = new ArrayList<>();
        for (Event e: allEvents) {
            EventRes eRes = new EventRes();
            eRes.setId(e.getId());
            eRes.setTitle(e.getTitle());
            eRes.setStartDate(e.getStartDate());
            eRes.setEndDate(e.getEndDate());
            eventRes.add(eRes);
        }
        if (allEvents != null) {
            return ResultVO.success(eventRes);
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
            eventService.updateVolunteerStatus(request.getId(), request.getStatus());
            return ResultVO.success("Status updated successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to update status");
        }
    }
}