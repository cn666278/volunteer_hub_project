package com.wsa.service;

import com.wsa.model.Event;
import com.wsa.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    public List<Event> getEventsByMonth(int month, int year) {
        return eventMapper.findEventsByMonth(month, year);
    }
}