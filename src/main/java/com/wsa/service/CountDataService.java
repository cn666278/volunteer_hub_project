package com.wsa.service;

import com.wsa.mapper.CountDataMapper;
import com.wsa.model.CountDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing and retrieving statistical data.
 * This service interacts with the data access layer (CountDataMapper) to fetch various counts related to events and users.
 */
@Service
public class CountDataService {

    @Autowired
    private CountDataMapper countDataMapper; // Mapper for database interactions related to count data

    /**
     * Method to get a list of statistical data.
     * @return List of CountDataRes objects containing different statistical metrics.
     */
    public List<CountDataRes> getCountData() {
        List<CountDataRes> countDataResList = new ArrayList<>(); // Initialize list to store count data results

        // Retrieve and add total number of events
        CountDataRes totalEvents = new CountDataRes();
        totalEvents.setName("totalEvents");
        totalEvents.setIcon("histogram");
        totalEvents.setColor("#2ec7c9");
        totalEvents.setValue(countDataMapper.getTotalEvents());
        countDataResList.add(totalEvents);

        // Retrieve and add total number of users
        CountDataRes totalUsers = new CountDataRes();
        totalUsers.setName("totalUsers");
        totalUsers.setIcon("user-filled");
        totalUsers.setColor("#5ab1ef");
        totalUsers.setValue(countDataMapper.getTotalUsers());
        countDataResList.add(totalUsers);

        // Retrieve and add total number of registered users
        CountDataRes totalRegisterUsers = new CountDataRes();
        totalRegisterUsers.setName("totalRegisterUsers");
        totalRegisterUsers.setIcon("checked");
        totalRegisterUsers.setColor("#ffb980");
        totalRegisterUsers.setValue(countDataMapper.getTotalRegisterUsers());
        countDataResList.add(totalRegisterUsers);

        // Retrieve and add today's active events
        CountDataRes todaysActiveEvents = new CountDataRes();
        todaysActiveEvents.setName("todaysActiveEvents");
        todaysActiveEvents.setIcon("histogram");
        todaysActiveEvents.setColor("#2ec7c9");
        todaysActiveEvents.setValue(countDataMapper.getTodaysActiveEvents());
        countDataResList.add(todaysActiveEvents);

        // Retrieve and add today's active users
        CountDataRes todaysActiveUsers = new CountDataRes();
        todaysActiveUsers.setName("todaysActiveUsers");
        todaysActiveUsers.setIcon("user-filled");
        todaysActiveUsers.setColor("#5ab1ef");
        todaysActiveUsers.setValue(countDataMapper.getTodaysActiveUsers());
        countDataResList.add(todaysActiveUsers);

        // Retrieve and add today's registered users
        CountDataRes todaysRegisterUsers = new CountDataRes();
        todaysRegisterUsers.setName("todaysRegisterUsers");
        todaysRegisterUsers.setIcon("checked");
        todaysRegisterUsers.setColor("#ffb980");
        todaysRegisterUsers.setValue(countDataMapper.getTodaysRegisterUsers());
        countDataResList.add(todaysRegisterUsers);

        // Retrieve and add monthly active events
        CountDataRes monthlyActiveEvents = new CountDataRes();
        monthlyActiveEvents.setName("monthlyActiveEvents");
        monthlyActiveEvents.setIcon("histogram");
        monthlyActiveEvents.setColor("#2ec7c9");
        monthlyActiveEvents.setValue(countDataMapper.getMonthlyActiveEvents());
        countDataResList.add(monthlyActiveEvents);

        // Retrieve and add monthly active users
        CountDataRes monthlyActiveUsers = new CountDataRes();
        monthlyActiveUsers.setName("monthlyActiveUsers");
        monthlyActiveUsers.setIcon("user-filled");
        monthlyActiveUsers.setColor("#5ab1ef");
        monthlyActiveUsers.setValue(countDataMapper.getMonthlyActiveUsers());
        countDataResList.add(monthlyActiveUsers);

        // Retrieve and add monthly registered users
        CountDataRes monthlyRegisterUsers = new CountDataRes();
        monthlyRegisterUsers.setName("monthlyRegisterUsers");
        monthlyRegisterUsers.setIcon("checked");
        monthlyRegisterUsers.setColor("#ffb980");
        monthlyRegisterUsers.setValue(countDataMapper.getMonthlyRegisterUsers());
        countDataResList.add(monthlyRegisterUsers);

        // Return the list of all statistical data collected
        return countDataResList;
    }
}
