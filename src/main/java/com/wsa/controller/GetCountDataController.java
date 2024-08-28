package com.wsa.controller;

import com.wsa.model.CountDataRes;
import com.wsa.model.ResultVO;
import com.wsa.service.CountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin") // Base URL for all admin-related endpoints
public class GetCountDataController {

    @Autowired
    private CountDataService countDataService; // Service for fetching count-related data

    /**
     * Endpoint to retrieve statistical count data.
     * @return ResultVO containing a list of CountDataRes objects with statistical data.
     */
    @GetMapping("/getCountData")
    public ResultVO<List<CountDataRes>> getCountData() {
        List<CountDataRes> stats = countDataService.getCountData(); // Fetch count data from the service
        return ResultVO.success(stats); // Return the data wrapped in a ResultVO success response
    }
}
