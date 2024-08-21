package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventRolesMapper {

void saveEventRoles(EventRoles eventRoles);

    void deleteEventRolesByEventId(Long id);

    @Select("SELECT * FROM eventroles WHERE eventId = #{eventId}")
    List<EventRoles> findRolesByEventId(@Param("eventId") Long eventId);
}