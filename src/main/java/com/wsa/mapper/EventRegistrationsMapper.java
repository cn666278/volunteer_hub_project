package com.wsa.mapper;

import com.wsa.model.EventRegistrations;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventRegistrationsMapper {

    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    @Select("SELECT COUNT(*) FROM eventregistrations WHERE eventId = #{eventId} AND volunteerId = #{volunteerId}")
    int countByEventIdAndVolunteerId(@Param("eventId") Long eventId, @Param("volunteerId") Long volunteerId);

    @Insert("INSERT INTO eventregistrations (eventId, volunteerId, roleId, status) VALUES (#{eventId}, #{volunteerId}, #{roleId}, #{status})")
    void saveEventRegistration(EventRegistrations eventRegistration);

    @Select("SELECT * FROM eventregistrations WHERE volunteerId = #{volunteerId} AND status = 'subscribed'")
    List<EventRegistrations> findByVolunteerIdAndStatus(@Param("volunteerId") Long volunteerId, @Param("status") String status);
}
