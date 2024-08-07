package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping("/getUserByOrganizerId")
    public ResultVO<UserInfo> getUserByOrganizerId(@RequestBody OrganizerRequest request) {
        UserInfo userInfo = organizerService.getUserInfoByOrganizerId(request.getOrganizerId());
        if (userInfo != null) {
            return ResultVO.success(userInfo);
        } else {
            return ResultVO.failure("Organizer not found for organizerId: " + request.getOrganizerId());
        }
    }

    @GetMapping("/getAllOrganizers")
    public ResultVO<List<Organizer>> getAllOrganizers(@RequestParam(defaultValue = "1") int current,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        List<Organizer> organizers = organizerService.getOrganizersByPage(current, pageSize);
        if (organizers != null) {
            return ResultVO.success(organizers);
        } else {
            return ResultVO.failure("No organizers found");
        }
    }
}
