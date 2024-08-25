package com.wsa.lightweightmockmvctests;

import com.wsa.controller.EchartDataController;
import com.wsa.model.EchartDataRes;
import com.wsa.model.ResultVO;
import com.wsa.service.EchartDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EchartDataController.class)
public class EchartDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EchartDataService echartDataService;

    @InjectMocks
    private EchartDataController echartDataController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(echartDataController).build();
    }

    @Test
    public void testGetEchartData_Success() throws Exception {
        // Given
        EchartDataRes echartDataRes = new EchartDataRes();
        // Set necessary fields of echartDataRes
        when(echartDataService.getEchartData()).thenReturn(echartDataRes);

        // When & Then
        mockMvc.perform(get("/admin/getEchartData")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200")) // Assuming ResultVO success method returns code 200
                .andExpect(jsonPath("$.data").isNotEmpty()); // Checks that data is present
    }
}
