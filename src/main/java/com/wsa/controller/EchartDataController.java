package com.wsa.controller;

import com.wsa.model.EchartDataRes;
import com.wsa.model.ResultVO;
import com.wsa.service.EchartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class EchartDataController {

    @Autowired
    private EchartDataService echartDataService;

    @GetMapping("/getEchartData")
    public ResultVO<EchartDataRes> getEchartData() {
        EchartDataRes echartData = echartDataService.getEchartData();
        return ResultVO.success(echartData);
    }
}
