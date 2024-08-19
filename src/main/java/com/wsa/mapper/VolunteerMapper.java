package com.wsa.mapper;

import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    List<Volunteer> getVolunteersByEventId(@Param("eventId") Long eventId, @Param("status") String status);

    void insertVolunteerRating(SubmitCommentRequest request);
    @Select("SELECT * FROM volunteer WHERE userId = #{userId}")
    Volunteer getVolunteerByUserId(Long userId);
    @Insert("INSERT INTO volunteer (userId) VALUES (#{userId})")
    void addVolunteer(Volunteer volunteer);
    @Update("UPDATE volunteer SET kudosPoints = kudosPoints + #{pointsAwarded} WHERE id = #{volunteerId}")
    void addPointsAwarded(Long volunteerId, Integer pointsAwarded);
}