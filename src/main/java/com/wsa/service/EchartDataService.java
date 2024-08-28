package com.wsa.service;

import com.wsa.mapper.EchartDataMapper;
import com.wsa.model.EchartDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EchartDataService {

    @Autowired
    private EchartDataMapper echartDataMapper; // Mapper for database interactions related to Echart data

    /**
     * Method to retrieve and format data for Echarts visualization.
     * @return EchartDataRes object containing event, organizer, and user data for visualization.
     */
    public EchartDataRes getEchartData() {
        EchartDataRes echartDataRes = new EchartDataRes();

        // Retrieve event data
        EchartDataRes.EventData eventData = new EchartDataRes.EventData();
        List<String> dates = echartDataMapper.getDates(); // Retrieve the dates for the current week

        List<Map<String, Integer>> formattedEventData = new ArrayList<>();

        // Iterate over each date to retrieve the events and registration counts for that day
        for (String date : dates) {
            List<Map<String, Object>> dailyEventData = echartDataMapper.getEventDataByDate(date);
            Map<String, Integer> dailyEventMap = new HashMap<>();

            // Process each record to map the event type to the number of registrations
            for (Map<String, Object> record : dailyEventData) {
                String eventType = (String) record.get("eventType");
                Integer registerCount = ((Number) record.get("register")).intValue();
                dailyEventMap.put(eventType, registerCount);
            }

            formattedEventData.add(dailyEventMap);
        }

        // Set event data for Echarts visualization
        eventData.setDate(dates);
        eventData.setData(formattedEventData);
        echartDataRes.setEventData(eventData);

        // Retrieve organizer data and set it for Echarts visualization
        List<EchartDataRes.OrganizerData> organizerData = echartDataMapper.getOrganizerData();
        echartDataRes.setOrganizerData(organizerData);

        // Retrieve user data and set it for Echarts visualization
        List<EchartDataRes.UserData> userData = echartDataMapper.getUserData();
        echartDataRes.setUserData(userData);

        return echartDataRes; // Return the complete Echart data response
    }
}
