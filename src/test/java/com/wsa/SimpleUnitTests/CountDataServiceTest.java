package com.wsa.SimpleUnitTests;

import com.wsa.mapper.CountDataMapper;
import com.wsa.model.CountDataRes;
import com.wsa.service.CountDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CountDataServiceTest {

    @Mock
    private CountDataMapper countDataMapper;

    @InjectMocks
    private CountDataService countDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCountData() {
        // Arrange
        when(countDataMapper.getTotalEvents()).thenReturn(10);
        when(countDataMapper.getTotalUsers()).thenReturn(100);
        when(countDataMapper.getTotalRegisterUsers()).thenReturn(50);
        when(countDataMapper.getTodaysActiveEvents()).thenReturn(5);
        when(countDataMapper.getTodaysActiveUsers()).thenReturn(20);
        when(countDataMapper.getTodaysRegisterUsers()).thenReturn(10);
        when(countDataMapper.getMonthlyActiveEvents()).thenReturn(15);
        when(countDataMapper.getMonthlyActiveUsers()).thenReturn(200);
        when(countDataMapper.getMonthlyRegisterUsers()).thenReturn(100);

        // Act
        List<CountDataRes> countDataResList = countDataService.getCountData();

        // Assert
        assertThat(countDataResList).hasSize(9);
        assertThat(countDataResList.get(0).getName()).isEqualTo("totalEvents");
        assertThat(countDataResList.get(0).getValue()).isEqualTo(10);
        // Add more assertions as needed for other CountDataRes elements
    }
}
