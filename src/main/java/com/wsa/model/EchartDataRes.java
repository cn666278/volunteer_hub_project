package com.wsa.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class EchartDataRes implements Serializable {
    private static final long serialVersionUID = 1L;

    // eventData中的数据结构
    @Data
    public static class EventData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<String> date;
        private List<Map<String, Integer>> data;
    }

    // organizerData中的数据结构
    @Data
    public static class OrganizerData implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private Integer value;
    }

    // userData中的数据结构
    @Data
    public static class UserData implements Serializable {
        private static final long serialVersionUID = 1L;
        private String date;
        private Integer newUsers;
        private Integer activeUsers;
    }

    private EventData eventData;
    private List<OrganizerData> organizerData;
    private List<UserData> userData;
}
