package com.wsa.mapper;

import com.wsa.model.EchartDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EchartDataMapper {

    // 获取当前日期所在一周的日期列表（结束日期为今天）
    @Select("SELECT " +
            "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq DAY), '%Y-%m-%d') AS date " +
            "FROM (" +
            "SELECT @row := @row + 1 AS seq " +
            "FROM (" +
            "SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6" +
            ") t1, (" +
            "SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6" +
            ") t2, (" +
            "SELECT @row := -1" +
            ") t3" +
            ") seqs " +
            "WHERE seq < 7 " +
            "ORDER BY seq ASC")
    List<String> getDates();

    // 获取某一天的事件和注册人数
    @Select("SELECT " +
            "e.title AS eventType, " +
            "COUNT(er.id) AS register " +
            "FROM events e " +
            "JOIN eventregistrations er ON e.id = er.eventId " +
            "WHERE er.status = 'accepted' " +
            "AND DATE(er.registrationTime) = #{date} " +
            "GROUP BY e.title")
    List<Map<String, Object>> getEventDataByDate(@Param("date") String date);

    // 获取organizerData中的数据
    @Select("SELECT " +
            "o.organizationName AS name, " +
            "COUNT(e.id) AS value " +
            "FROM organizer o " +
            "LEFT JOIN events e ON o.userId = e.organizerId " +
            "GROUP BY o.organizationName")
    List<EchartDataRes.OrganizerData> getOrganizerData();

    // 获取 userData 中的数据
    @Select("SELECT " +
            "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq DAY), '%a') AS date, " +
            "COALESCE(SUM(CASE WHEN DATE(registerTime) = DATE(DATE_SUB(CURDATE(), INTERVAL seq DAY)) THEN 1 ELSE 0 END), 0) AS newUsers, " +
            "COALESCE(SUM(CASE WHEN DATE(lastLoginTime) = DATE(DATE_SUB(CURDATE(), INTERVAL seq DAY)) THEN 1 ELSE 0 END), 0) AS activeUsers " +
            "FROM (SELECT @row := @row + 1 AS seq FROM (" +
            "SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6) t1, " +
            "(SELECT @row := -1) t2) seqs " +
            "LEFT JOIN users u ON DATE(u.registerTime) = DATE(DATE_SUB(CURDATE(), INTERVAL seq DAY)) " +
            "OR DATE(u.lastLoginTime) = DATE(DATE_SUB(CURDATE(), INTERVAL seq DAY)) " +
            "GROUP BY seq " +
            "ORDER BY FIELD(DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq DAY), '%a'), 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun')")
    List<EchartDataRes.UserData> getUserData();
}
