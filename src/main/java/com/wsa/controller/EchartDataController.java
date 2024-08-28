package com.wsa.controller;

import com.wsa.model.EchartDataRes;
import com.wsa.model.ResultVO;
import com.wsa.service.EchartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin") // Base URL for all endpoints in this controller
public class EchartDataController {

    @Autowired
    private EchartDataService echartDataService; // Service for fetching data for Echarts

    /**
     * Endpoint to retrieve data for Echarts visualization.
     * @return ResultVO<EchartDataRes> containing the data required for Echarts.
     */
    @GetMapping("/getEchartData")
    public ResultVO<EchartDataRes> getEchartData() {
        // Fetch the data required for Echarts visualization
        EchartDataRes echartData = echartDataService.getEchartData();

        // Return the fetched data wrapped in a success response object
        return ResultVO.success(echartData);
    }
}
