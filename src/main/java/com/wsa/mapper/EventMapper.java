package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventReqByOrganizerId;
import com.wsa.model.EventRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

@Mapper
public interface EventMapper {

    void saveEvent(Event event);

    List<Event> findAllEvents();

    List<Event> findEventsByMonth(int month, int year);

    List<Event> findEventsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    int getTotalCountByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    EventRequest getEventDetailById(Long id);

    void editEventById(Event event);
}