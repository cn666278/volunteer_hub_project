package com.wsa.service;

import com.wsa.mapper.EchartDataMapper;
import com.wsa.model.EchartDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EchartDataService {

    @Autowired
    private EchartDataMapper echartDataMapper;

    public EchartDataRes getEchartData() {
        EchartDataRes echartDataRes = new EchartDataRes();

        // 获取eventData
        EchartDataRes.EventData eventData = new EchartDataRes.EventData();
        eventData.setDate(echartDataMapper.getDates());  // 获取当前一周的日期
        eventData.setData(echartDataMapper.getEventData());  // 获取当前一周的事件数据
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
