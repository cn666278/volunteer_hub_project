package com.wsa.service;

import com.wsa.mapper.CountDataMapper;
import com.wsa.model.CountDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountDataService {

    @Autowired
    private CountDataMapper countDataMapper;

    // 获取统计数据的方法
    public List<CountDataRes> getCountData() {
        List<CountDataRes> countDataResList = new ArrayList<>();

        // totalEvents
        CountDataRes totalEvents = new CountDataRes();
        totalEvents.setName("totalEvents");
        totalEvents.setIcon("histogram");
        totalEvents.setColor("#2ec7c9");
        totalEvents.setValue(countDataMapper.getTotalEvents());
        countDataResList.add(totalEvents);

        // totalUsers
        CountDataRes totalUsers = new CountDataRes();
        totalUsers.setName("totalUsers");
        totalUsers.setIcon("user-filled");
        totalUsers.setColor("#5ab1ef");
        totalUsers.setValue(countDataMapper.getTotalUsers());
        countDataResList.add(totalUsers);

        // totalRegisterUsers
        CountDataRes totalRegisterUsers = new CountDataRes();
        totalRegisterUsers.setName("totalRegisterUsers");
        totalRegisterUsers.setIcon("checked");
        totalRegisterUsers.setColor("#ffb980");
        totalRegisterUsers.setValue(countDataMapper.getTotalRegisterUsers());
        countDataResList.add(totalRegisterUsers);

        // todaysActiveEvents
        CountDataRes todaysActiveEvents = new CountDataRes();
        todaysActiveEvents.setName("todaysActiveEvents");
        todaysActiveEvents.setIcon("histogram");
        todaysActiveEvents.setColor("#2ec7c9");
        todaysActiveEvents.setValue(countDataMapper.getTodaysActiveEvents());
        countDataResList.add(todaysActiveEvents);

        // todaysActiveUsers
        CountDataRes todaysActiveUsers = new CountDataRes();
        todaysActiveUsers.setName("todaysActiveUsers");
        todaysActiveUsers.setIcon("user-filled");
        todaysActiveUsers.setColor("#5ab1ef");
        todaysActiveUsers.setValue(countDataMapper.getTodaysActiveUsers());
        countDataResList.add(todaysActiveUsers);

        // todaysRegisterUsers
        CountDataRes todaysRegisterUsers = new CountDataRes();
        todaysRegisterUsers.setName("todaysRegisterUsers");
        todaysRegisterUsers.setIcon("checked");
        todaysRegisterUsers.setColor("#ffb980");
        todaysRegisterUsers.setValue(countDataMapper.getTodaysRegisterUsers());
        countDataResList.add(todaysRegisterUsers);

        // monthlyActiveEvents
        CountDataRes monthlyActiveEvents = new CountDataRes();
        monthlyActiveEvents.setName("monthlyActiveEvents");
        monthlyActiveEvents.setIcon("histogram");
        monthlyActiveEvents.setColor("#2ec7c9");
        monthlyActiveEvents.setValue(countDataMapper.getMonthlyActiveEvents());
        countDataResList.add(monthlyActiveEvents);

        // monthlyActiveUsers
        CountDataRes monthlyActiveUsers = new CountDataRes();
        monthlyActiveUsers.setName("monthlyActiveUsers");
        monthlyActiveUsers.setIcon("user-filled");
        monthlyActiveUsers.setColor("#5ab1ef");
        monthlyActiveUsers.setValue(countDataMapper.getMonthlyActiveUsers());
        countDataResList.add(monthlyActiveUsers);

        // monthlyRegisterUsers
        CountDataRes monthlyRegisterUsers = new CountDataRes();
        monthlyRegisterUsers.setName("monthlyRegisterUsers");
        monthlyRegisterUsers.setIcon("checked");
        monthlyRegisterUsers.setColor("#ffb980");
        monthlyRegisterUsers.setValue(countDataMapper.getMonthlyRegisterUsers());
        countDataResList.add(monthlyRegisterUsers);

        return countDataResList;
    }
}
