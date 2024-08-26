package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventRegistrations;
import com.wsa.model.EventReqByOrganizerId;
import com.wsa.model.EventRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    // 插入注册信息到 eventregistrations 表
    @Insert("INSERT INTO eventregistrations (eventId, volunteerId, roleId, status) " +
            "VALUES (#{eventId}, #{volunteerId}, #{roleId}, #{status})")
    void saveEventRegistration(EventRegistrations eventRegistration);

    // 检查某个志愿者是否已经为某个事件注册
    @Select("SELECT COUNT(*) FROM eventregistrations WHERE eventId = #{eventId} AND volunteerId = #{volunteerId}")
    int countByEventIdAndVolunteerId(@Param("eventId") Long eventId, @Param("volunteerId") Long volunteerId);

    @Select("SELECT e.id,e.organizerId,e.title,e.description,e.location,e.startDate,e.endDate,e.pointsAwarded,e.eventPic, er.status FROM events e " +
            "JOIN eventregistrations er ON e.id = er.eventId " +
            "WHERE er.volunteerId = #{volunteerId} AND er.status IN ('pending', 'accepted')")
    List<Event> findParticipatedEventsByVolunteerId(@Param("volunteerId") Long volunteerId);

    @Select("SELECT * FROM events WHERE status = 'Passed' ORDER BY startDate DESC LIMIT 3")
    List<Event> findLatestEvents();

    @Update("UPDATE events SET status = #{status} WHERE id = #{id}")
    void approveEvent(Event event);

    @Update("UPDATE events SET status = #{status} WHERE id = #{id}")
    void rejectEvent(Event event);
}