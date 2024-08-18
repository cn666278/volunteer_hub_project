package com.wsa.mapper;

import com.wsa.model.CountDataRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CountDataMapper {

    // 获取总事件数
    @Select("SELECT COUNT(*) FROM events")
    int getTotalEvents();

    // 获取今日活跃事件数
    @Select("SELECT COUNT(*) FROM events WHERE DATE(startDate) = CURDATE()")
    int getTodaysActiveEvents();

    // 获取本月活跃事件数
    @Select("SELECT COUNT(*) FROM events WHERE MONTH(startDate) = MONTH(CURDATE()) AND YEAR(startDate) = YEAR(CURDATE())")
    int getMonthlyActiveEvents();

    // 获取总用户数
    @Select("SELECT COUNT(*) FROM users")
    int getTotalUsers();

    // 获取今日活跃用户数
    @Select("SELECT COUNT(*) FROM users WHERE DATE(lastLoginTime) = CURDATE()")
    int getTodaysActiveUsers();

    // 获取本月活跃用户数
    @Select("SELECT COUNT(*) FROM users WHERE MONTH(lastLoginTime) = MONTH(CURDATE()) AND YEAR(lastLoginTime) = YEAR(CURDATE())")
    int getMonthlyActiveUsers();

    // 获取总注册用户数
    @Select("SELECT COUNT(*) FROM users WHERE DATE(registerTime) = CURDATE()")
    int getTotalRegisterUsers();

    // 获取今日注册用户数
    @Select("SELECT COUNT(*) FROM users WHERE DATE(registerTime) = CURDATE()")
    int getTodaysRegisterUsers();

    // 获取本月注册用户数
    @Select("SELECT COUNT(*) FROM users WHERE MONTH(registerTime) = MONTH(CURDATE()) AND YEAR(registerTime) = YEAR(CURDATE())")
    int getMonthlyRegisterUsers();

}

