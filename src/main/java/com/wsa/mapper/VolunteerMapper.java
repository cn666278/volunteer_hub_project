package com.wsa.mapper;

import com.wsa.model.Volunteer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    List<Volunteer> getVolunteersByEventId(Long eventId);
}