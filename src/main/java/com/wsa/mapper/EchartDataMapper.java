package com.wsa.mapper;

import com.wsa.model.EchartDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EchartDataMapper {

    // 获取当前日期所在一周的日期列表（结束日期为今天）
    @Select("""
        SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq DAY), '%Y%m%d') AS date
        FROM (
            SELECT @row := @row + 1 AS seq
            FROM (
                SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
            ) t1, (
                SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
            ) t2, (
                SELECT @row := -1
            ) t3
        ) seqs
        WHERE seq < 7
        ORDER BY seq ASC
        """)
    List<String> getDates();

    // 获取 eventData 中的数据
    @Select("""
        SELECT e.title AS name, COUNT(er.id) AS value
        FROM events e
        LEFT JOIN eventregistrations er ON e.id = er.eventId AND er.status = 'accepted'
        WHERE DATE(e.endDate) >= CURDATE() - INTERVAL 6 DAY
        GROUP BY e.title
        """)
    List<Map<String, Integer>> getEventData();

    // 获取organizerData中的数据
    @Select("SELECT o.organizationName AS name, COUNT(e.id) AS value " +
            "FROM organizer o " +
            "LEFT JOIN events e ON o.id = e.organizerId " +
            "GROUP BY o.organizationName")
    List<EchartDataRes.OrganizerData> getOrganizerData();

    // 获取 userData 中的数据
    @Select("""
        SELECT
            DAYNAME(er.created_at) AS date,
            SUM(CASE WHEN er.status = 'accepted' THEN 1 ELSE 0 END) AS newUsers,
            COUNT(er.id) AS activeUsers
        FROM eventregistrations er
        JOIN events e ON er.eventId = e.id
        WHERE DATE(er.created_at) BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE()
        GROUP BY DAYNAME(er.created_at)
        ORDER BY FIELD(DAYNAME(er.created_at), 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun')
        """)
    List<EchartDataRes.UserData> getUserData();
}
