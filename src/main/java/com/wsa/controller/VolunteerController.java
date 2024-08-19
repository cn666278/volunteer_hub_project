package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.Credential;
import com.wsa.model.ResultVO;
import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import com.wsa.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/getVolunteersByEventId")
    public ResultVO<PageInfo<Volunteer>> getVolunteersByEventId(
            @RequestParam Long eventId,
            @RequestParam String status,
            @RequestParam int pageNum,
            @RequestParam int pageSize) {
        PageInfo<Volunteer> volunteersByEventId = volunteerService.getVolunteersByEventId(eventId, status, pageNum, pageSize);
        for (Volunteer v : volunteersByEventId.getList()) {
            switch(v.getRoleId().intValue()){
                case 1 :
                    v.setRoleName("Event Coordinator");
                    break;
                case 2 :
                    v.setRoleName("Event Welcome Desk");
                    break;
                case 3 :
                    v.setRoleName("Athlete Registration Desk");
                    break;
                case 4 :
                    v.setRoleName("Transport Operations");
                    break;
                case 5 :
                    v.setRoleName("Event Greeter / Fan Experience");
                    break;
                case 6 :
                    v.setRoleName("Entertainment Coordinator");
                    break;
                default :
            }
        }
        return ResultVO.success(volunteersByEventId);
    }

    @PostMapping("/submitComment")
    public ResultVO<String> submitComment(@RequestBody SubmitCommentRequest request) {
        try {
            volunteerService.submitComment(request);
            return ResultVO.success("submitComment success");
        } catch (Exception e) {
            return ResultVO.failure("submitComment failure");
        }
    }

    @PostMapping("/getCredentialsByVolunteerId")
    public ResultVO<List<Credential>> getCredentialsByVolunteerId(@RequestBody Credential credential) {

        List<Credential> credentials = volunteerService.getCredentialsByVolunteerId(credential.getVolunteerId());
        if (credentials != null && !credentials.isEmpty()) {
            return ResultVO.success(credentials);
        } else {
            return ResultVO.failure("No credentials found for this volunteerId.");
        }
    }

    @PostMapping("/deleteCredential")
    public ResultVO<String> deleteCredential(@RequestBody Credential credential) {
        try {
            volunteerService.deleteCredentialById(credential.getId());
            return ResultVO.success("Credential deleted successfully.");
        } catch (Exception e) {
            return ResultVO.failure("Failed to delete credential.");
        }
    }

    @PostMapping("/updateCredential")
    public ResultVO<String> updateCredential(@RequestBody Credential credential) {
        try {
            volunteerService.updateCredential(credential);
            return ResultVO.success("Credential updated successfully.");
        } catch (Exception e) {
            return ResultVO.failure("Failed to update credential.");
        }
    }
}
