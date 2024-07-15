package com.wsa.mapper;

import com.wsa.model.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {

    void saveEvent(Event event);

    List<Event> findEventsByMonth(int month, int year);
}