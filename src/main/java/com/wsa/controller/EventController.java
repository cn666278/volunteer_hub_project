package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.*;
import com.wsa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

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
}