package com.wsa.service;

import com.wsa.mapper.EchartDataMapper;
import com.wsa.model.EchartDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EchartDataService {

    @Autowired
    private EchartDataMapper echartDataMapper;

    public EchartDataRes getEchartData() {
        EchartDataRes echartDataRes = new EchartDataRes();

        // 获取eventData
        EchartDataRes.EventData eventData = new EchartDataRes.EventData();
        List<String> dates = echartDataMapper.getDates(); // 获取当前一周的日期

        List<Map<String, Integer>> formattedEventData = new ArrayList<>();

        // 遍历每个日期，获取当天的事件和注册人数
        for (String date : dates) {
            List<Map<String, Object>> dailyEventData = echartDataMapper.getEventDataByDate(date);
            Map<String, Integer> dailyEventMap = new HashMap<>();

            for (Map<String, Object> record : dailyEventData) {
                String eventType = (String) record.get("eventType");
                Integer registerCount = ((Number) record.get("register")).intValue();
                dailyEventMap.put(eventType, registerCount);
            }

            formattedEventData.add(dailyEventMap);
        }

        eventData.setDate(dates);
        eventData.setData(formattedEventData);
        echartDataRes.setEventData(eventData);

        // 获取organizerData
        List<EchartDataRes.OrganizerData> organizerData = echartDataMapper.getOrganizerData();
        echartDataRes.setOrganizerData(organizerData);

        // 获取userData
        List<EchartDataRes.UserData> userData = echartDataMapper.getUserData();
        echartDataRes.setUserData(userData);

        return echartDataRes;
    }
}
