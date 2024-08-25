package com.wsa.SimpleUnitTests;

import com.wsa.mapper.EchartDataMapper;
import com.wsa.model.EchartDataRes;
import com.wsa.service.EchartDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EchartDataServiceTest {

    @Mock
    private EchartDataMapper echartDataMapper;

    @InjectMocks
    private EchartDataService echartDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetEchartData() {
        // Arrange
        List<String> dates = Arrays.asList("2024-08-20", "2024-08-21");
        when(echartDataMapper.getDates()).thenReturn(dates);

        List<Map<String, Object>> eventDataForDate1 = new ArrayList<>();
        Map<String, Object> event1 = new HashMap<>();
        event1.put("eventType", "Conference");
        event1.put("register", 10);
        eventDataForDate1.add(event1);

        when(echartDataMapper.getEventDataByDate("2024-08-20")).thenReturn(eventDataForDate1);
        when(echartDataMapper.getEventDataByDate("2024-08-21")).thenReturn(new ArrayList<>());

        List<EchartDataRes.OrganizerData> organizerData = new ArrayList<>();
        List<EchartDataRes.UserData> userData = new ArrayList<>();

        when(echartDataMapper.getOrganizerData()).thenReturn(organizerData);
        when(echartDataMapper.getUserData()).thenReturn(userData);

        // Act
        EchartDataRes result = echartDataService.getEchartData();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEventData().getDate()).isEqualTo(dates);
        assertThat(result.getEventData().getData().get(0)).containsEntry("Conference", 10);
        assertThat(result.getOrganizerData()).isSameAs(organizerData);
        assertThat(result.getUserData()).isSameAs(userData);
    }
}
