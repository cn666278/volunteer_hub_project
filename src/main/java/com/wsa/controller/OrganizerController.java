package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizer") // Base URL for all organizer-related endpoints
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService; // Service for managing organizer-related operations

    /**
     * Endpoint to retrieve user information associated with a given organizer ID.
     * @param request The request object containing the organizer ID.
     * @return ResultVO containing the user information if found, otherwise a failure message.
     */
    @PostMapping("/getUserByOrganizerId")
    public ResultVO<UserInfo> getUserByOrganizerId(@RequestBody OrganizerRequest request) {
        UserInfo userInfo = organizerService.getUserInfoByOrganizerId(request.getOrganizerId()); // Fetch user info by organizer ID
        if (userInfo != null) {
            return ResultVO.success(userInfo); // Return user info if found
        } else {
            return ResultVO.failure("Organizer not found for organizerId: " + request.getOrganizerId()); // Return failure message if not found
        }
    }

    /**
     * Endpoint to retrieve a paginated list of all organizers.
     * @param current The current page number (default is 1).
     * @param pageSize The number of records per page (default is 10).
     * @return ResultVO containing a list of organizers or a failure message if none found.
     */
    @GetMapping("/getAllOrganizers")
    public ResultVO<List<Organizer>> getAllOrganizers(@RequestParam(defaultValue = "1") int current,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        List<Organizer> organizers = organizerService.getOrganizersByPage(current, pageSize); // Fetch organizers with pagination
        if (organizers != null) {
            return ResultVO.success(organizers); // Return list of organizers if found
        } else {
            return ResultVO.failure("No organizers found"); // Return failure message if no organizers found
        }
    }
}
