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
@RequestMapping("/admin")
public class GetCountDataController {

    @Autowired
    private CountDataService countDataService;

    // 获取统计数据的接口
    @GetMapping("/getCountData")
    public ResultVO<List<CountDataRes>> getCountData() {
        List<CountDataRes> stats = countDataService.getCountData();
        return ResultVO.success(stats);
    }
}
