package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventReqByOrganizerId;
import com.wsa.model.EventRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {

    void saveEvent(Event event);

    List<Event> findEventsByMonth(int month, int year);

    List<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    int getTotalCountByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    EventRequest getEventDetailById(Long id);

    void editEventById(Event event);
}