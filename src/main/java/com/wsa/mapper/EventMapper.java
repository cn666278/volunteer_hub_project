package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventReqByOrganizerId;
import com.wsa.model.EventRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface EventMapper {

    void saveEvent(Event event);

    @Select("SELECT * FROM events LIMIT #{offset}, #{pageSize}")
    List<Event> getEventsByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int getTotalEventsCount();

    List<Event> findAllEvents();

    @Select("SELECT * FROM events WHERE id = #{id}")
    Event getEventById(@Param("id") Long id);

    List<Event> findEventsByMonth(int month, int year);

    List<Event> findEventsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Event> getEventsByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    int getTotalCountByOrganizerIdAndFilters(EventReqByOrganizerId eventRequest);

    EventRequest getEventDetailById(Long id);

    void editEventById(Event event);



}