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
        totalEvents.setIcon("success-filled");
        totalEvents.setColor("#2ec7c9");
        totalEvents.setValue(countDataMapper.getTotalEvents());
        countDataResList.add(totalEvents);

        // totalUsers
        CountDataRes totalUsers = new CountDataRes();
        totalUsers.setName("totalUsers");
        totalUsers.setIcon("star-filled");
        totalUsers.setColor("#ffb980");
        totalUsers.setValue(countDataMapper.getTotalUsers());
        countDataResList.add(totalUsers);

        // totalRegisterUsers
        CountDataRes totalRegisterUsers = new CountDataRes();
        totalRegisterUsers.setName("totalRegisterUsers");
        totalRegisterUsers.setIcon("goods-filled");
        totalRegisterUsers.setColor("#5ab1ef");
        totalRegisterUsers.setValue(countDataMapper.getTotalRegisterUsers());
        countDataResList.add(totalRegisterUsers);

        // todaysActiveEvents
        CountDataRes todaysActiveEvents = new CountDataRes();
        todaysActiveEvents.setName("todaysActiveEvents");
        todaysActiveEvents.setIcon("success-filled");
        todaysActiveEvents.setColor("#2ec7c9");
        todaysActiveEvents.setValue(countDataMapper.getTodaysActiveEvents());
        countDataResList.add(todaysActiveEvents);

        // todaysActiveUsers
        CountDataRes todaysActiveUsers = new CountDataRes();
        todaysActiveUsers.setName("todaysActiveUsers");
        todaysActiveUsers.setIcon("star-filled");
        todaysActiveUsers.setColor("#ffb980");
        todaysActiveUsers.setValue(countDataMapper.getTodaysActiveUsers());
        countDataResList.add(todaysActiveUsers);

        // todaysRegisterUsers
        CountDataRes todaysRegisterUsers = new CountDataRes();
        todaysRegisterUsers.setName("todaysRegisterUsers");
        todaysRegisterUsers.setIcon("goods-filled");
        todaysRegisterUsers.setColor("#5ab1ef");
        todaysRegisterUsers.setValue(countDataMapper.getTodaysRegisterUsers());
        countDataResList.add(todaysRegisterUsers);

        // monthlyActiveEvents
        CountDataRes monthlyActiveEvents = new CountDataRes();
        monthlyActiveEvents.setName("monthlyActiveEvents");
        monthlyActiveEvents.setIcon("success-filled");
        monthlyActiveEvents.setColor("#2ec7c9");
        monthlyActiveEvents.setValue(countDataMapper.getMonthlyActiveEvents());
        countDataResList.add(monthlyActiveEvents);

        // monthlyActiveUsers
        CountDataRes monthlyActiveUsers = new CountDataRes();
        monthlyActiveUsers.setName("monthlyActiveUsers");
        monthlyActiveUsers.setIcon("star-filled");
        monthlyActiveUsers.setColor("#ffb980");
        monthlyActiveUsers.setValue(countDataMapper.getMonthlyActiveUsers());
        countDataResList.add(monthlyActiveUsers);

        // monthlyRegisterUsers
        CountDataRes monthlyRegisterUsers = new CountDataRes();
        monthlyRegisterUsers.setName("monthlyRegisterUsers");
        monthlyRegisterUsers.setIcon("goods-filled");
        monthlyRegisterUsers.setColor("#5ab1ef");
        monthlyRegisterUsers.setValue(countDataMapper.getMonthlyRegisterUsers());
        countDataResList.add(monthlyRegisterUsers);

        return countDataResList;
    }
}
