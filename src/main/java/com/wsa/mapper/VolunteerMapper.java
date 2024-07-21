package com.wsa.mapper;

import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    List<Volunteer> getVolunteersByEventId(@Param("eventId") Long eventId, @Param("status") String status);

    void insertVolunteerRating(SubmitCommentRequest request);
}