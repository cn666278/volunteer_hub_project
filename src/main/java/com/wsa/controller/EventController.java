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
@RequestMapping("/event") // Base URL for all event-related endpoints
public class EventController {

    @Autowired
    private EventService eventService; // Service for managing event-related operations

    @Autowired
    private OrganizerService organizerService; // Service for managing organizer-related operations

    @Autowired
    private VolunteerService volunteerService; // Service for managing volunteer-related operations

    /**
     * Get the list of all events.
     * @return A list of all events in the system.
     */
    @GetMapping("/getEventList")
    public ResultVO<List<EventRes>> getAllEventsList() {
        List<Event> allEvents = eventService.getAllEvents();
        List<EventRes> eventResList = new ArrayList<>();

        // Convert each Event entity to EventRes DTO for response
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
            return ResultVO.failure("No events found!");
        }
    }

    /**
     * Get events filtered by month and year.
     * @param month The month to filter events.
     * @param year The year to filter events.
     * @return A list of events occurring in the specified month and year.
     */
    @GetMapping("/getEventsByDate")
    public ResultVO<List<EventRes>> getEventsByDate(@RequestParam("month") int month, @RequestParam("year") int year) {
        List<Event> eventsByMonth = eventService.getEventsByMonth(month, year);
        List<EventRes> eventRes = new ArrayList<>();
        for (Event e : eventsByMonth) {
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
            return ResultVO.failure("No events found for the specified date!");
        }
    }

    /**
     * Get events within a specified date range.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return A list of events within the specified date range.
     */
    @GetMapping("/getEventsByDateRange")
    public ResultVO<List<EventRes>> getEventsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Event> eventsByDateRange = eventService.getEventsByDateRange(startDate, endDate);
        List<EventRes> eventRes = new ArrayList<>();
        for (Event e : eventsByDateRange) {
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
            return ResultVO.failure("No events found in the specified date range!");
        }
    }

    /**
     * Register a new event.
     * @param eventRequest The event request object containing event details.
     * @return Success message upon successful registration.
     */
    @PostMapping("/registerEvent")
    public ResultVO<String> registerEvent(@RequestBody EventRequest eventRequest) {
        eventService.registerEvent(eventRequest);
        return ResultVO.success("Event registered successfully");
    }

    /**
     * Get event details by event ID.
     * @param eventRequest The event request containing the event ID.
     * @return The event details.
     */
    @PostMapping("/getEventById")
    public ResultVO<Event> getEventById(@RequestBody EventRequest eventRequest) {
        Event event = eventService.getEventById(eventRequest.getEventId());
        return ResultVO.success(event);
    }

    /**
     * Get events by organizer ID with filters applied.
     * @param eventRequest The event request containing organizer ID and filters.
     * @return A paginated list of events filtered by organizer ID.
     */
    @PostMapping("/getEventsByOrganizerIdAndFilters")
    public ResultVO<PageInfo<EventRequest>> getEventsByOrganizerIdAndFilters(@RequestBody EventReqByOrganizerId eventRequest) {
        PageInfo<Event> events = eventService.getEventsByOrganizerIdAndFilters(eventRequest);
        PageInfo<EventRequest> eventRequestPageInfo = convertPageInfo(events);
        return ResultVO.success(eventRequestPageInfo);
    }

    // Convert PageInfo of Event to PageInfo of EventRequest
    public PageInfo<EventRequest> convertPageInfo(PageInfo<Event> eventsPage) {
        List<EventRequest> eventRequests = eventsPage.getList().stream()
                .map(event -> convertEventToEventRequest(event))
                .collect(Collectors.toList());

        PageInfo<EventRequest> eventRequestsPage = new PageInfo<>(eventRequests);
        // Copy pagination details from original PageInfo
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

    // Convert Event to EventRequest
    private EventRequest convertEventToEventRequest(Event event) {
        return eventService.getEventDetailById(event.getId());
    }

    /**
     * Edit event details by event ID.
     * @param eventRequest The request object containing updated event details.
     * @return Success message upon successful update.
     */
    @PostMapping("/editEventById")
    public ResultVO<String> editEventById(@RequestBody EventRequest eventRequest) {
        eventService.editEventById(eventRequest);
        return ResultVO.success("Event updated successfully");
    }

    /**
     * Update volunteer status for an event.
     * @param request The request containing volunteer status update details.
     * @return Success or failure message based on update result.
     */
    @PostMapping("/updateVolunteerStatus")
    public ResultVO<String> updateVolunteerStatus(@RequestBody UpdateStatusRequest request) {
        try {
            eventService.updateVolunteerStatus(request.getId(), request.getEmail(), request.getEventId(), request.getStatus());
            return ResultVO.success("Status updated successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to update status");
        }
    }

    // [Remaining methods will have similar comment structure explaining each endpoint]

    // Get statistics for events
    @GetMapping("/getEventStats")
    public ResultVO<List<EventDataRes>> getEventStats() {
        List<EventDataRes> stats = eventService.getEventStats();
        return ResultVO.success(stats);
    }

    /**
     * Approve an event.
     * @param id The request object containing the event ID.
     * @return Success or failure message based on the approval result.
     */
    @PostMapping("/approveEvent")
    public ResultVO<String> approveEvent(@RequestBody EventUpdateRes id) {
        try {
            eventService.updateEventStatusToPassed(id.getId());
            return ResultVO.success("Event approved successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to approve event: " + e.getMessage());
        }
    }

    /**
     * Reject an event.
     * @param id The request object containing the event ID.
     * @return Success or failure message based on the rejection result.
     */
    @PostMapping("/rejectEvent")
    public ResultVO<String> rejectEvent(@RequestBody EventUpdateRes id) {
        try {
            eventService.updateEventStatusToRejected(id.getId());
            return ResultVO.success("Event rejected successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to reject event: " + e.getMessage());
        }
    }
}
