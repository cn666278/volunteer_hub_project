package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.ResultVO;
import com.wsa.model.Volunteer;
import com.wsa.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/getVolunteersByEventId")
    public ResultVO<PageInfo<Volunteer>> getVolunteersByEventId(
            @RequestParam Long eventId,
            @RequestParam int pageNum,
            @RequestParam int pageSize) {
        PageInfo<Volunteer> volunteersByEventId = volunteerService.getVolunteersByEventId(eventId, pageNum, pageSize);
        for (Volunteer v : volunteersByEventId.getList()) {
            v.setRoleName("roleName" + v.getRoleId());
        }
        return ResultVO.success(volunteersByEventId);
    }
}
