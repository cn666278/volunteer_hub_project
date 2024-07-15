package com.wsa.controller;

import com.wsa.model.Event;
import com.wsa.model.EventRequest;
import com.wsa.model.EventRes;
import com.wsa.model.ResultVO;
import com.wsa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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
}