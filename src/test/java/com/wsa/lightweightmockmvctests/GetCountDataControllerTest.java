package com.wsa.lightweightmockmvctests;

import com.wsa.controller.GetCountDataController;
import com.wsa.model.CountDataRes;
import com.wsa.service.CountDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetCountDataController.class)
public class GetCountDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CountDataService countDataService;

    @InjectMocks
    private GetCountDataController getCountDataController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(getCountDataController).build();
    }

    @Test
    public void testGetCountData_Success() throws Exception {
        // Given
        List<CountDataRes> stats = new ArrayList<>();
        CountDataRes data1 = new CountDataRes();
        data1.setName("totalEvents");
        data1.setValue(100);
        stats.add(data1);

        CountDataRes data2 = new CountDataRes();
        data2.setName("totalUsers");
        data2.setValue(200);
        stats.add(data2);

        when(countDataService.getCountData()).thenReturn(stats);

        // When & Then
        mockMvc.perform(get("/admin/getCountData")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("totalEvents"))
                .andExpect(jsonPath("$.data[0].value").value(100))
                .andExpect(jsonPath("$.data[1].name").value("totalUsers"))
                .andExpect(jsonPath("$.data[1].value").value(200));
    }

    @Test
    public void testGetCountData_Empty() throws Exception {
        // Given
        when(countDataService.getCountData()).thenReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/admin/getCountData")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
