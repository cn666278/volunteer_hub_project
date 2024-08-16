package com.wsa.mapper;

import com.wsa.model.EventRegistrations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventRegistrationsMapper {

    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    // 获取今日活跃用户数（仅状态为“accepted”的用户）
    @Select("SELECT COUNT(DISTINCT u.id) " +
            "FROM eventregistrations er " +
            "JOIN users u ON er.volunteerId = u.id " +
            "WHERE er.eventId = #{eventId} " +
            "AND er.status = 'accepted' " +
            "AND DATE(u.lastLoginTime) = CURDATE()")
    Integer getTodayActiveUsers(@Param("eventId") Long eventId);

    // 获取本月活跃用户数（仅状态为“accepted”的用户）
    @Select("SELECT COUNT(DISTINCT u.id) " +
            "FROM eventregistrations er " +
            "JOIN users u ON er.volunteerId = u.id " +
            "WHERE er.eventId = #{eventId} " +
            "AND er.status = 'accepted' " +
            "AND MONTH(u.lastLoginTime) = MONTH(CURDATE()) " +
            "AND YEAR(u.lastLoginTime) = YEAR(CURDATE())")
    Integer getMonthlyActiveUsers(@Param("eventId") Long eventId);

    // 获取总用户数（仅状态为“accepted”的用户）
    @Select("SELECT COUNT(DISTINCT u.id) " +
            "FROM eventregistrations er " +
            "JOIN users u ON er.volunteerId = u.id " +
            "WHERE er.eventId = #{eventId} " +
            "AND er.status = 'accepted'")
    Integer getTotalUsers(@Param("eventId") Long eventId);
}
