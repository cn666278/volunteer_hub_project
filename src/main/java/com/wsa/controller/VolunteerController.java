package com.wsa.controller;

import com.github.pagehelper.PageInfo;
import com.wsa.model.*;
import com.wsa.service.EventService;
import com.wsa.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer") // Base URL for all volunteer-related endpoints
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService; // Service for managing volunteer-related operations

    @Autowired
    private EventService eventService; // Service for managing event-related operations

    /**
     * Endpoint to get a paginated list of volunteers by event ID and status.
     * @param eventId The ID of the event to filter volunteers.
     * @param status The status of volunteers to filter by.
     * @param pageNum The current page number for pagination.
     * @param pageSize The number of records per page.
     * @return ResultVO containing a paginated list of volunteers.
     */
    @GetMapping("/getVolunteersByEventId")
    public ResultVO<PageInfo<Volunteer>> getVolunteersByEventId(
            @RequestParam Long eventId,
            @RequestParam String status,
            @RequestParam int pageNum,
            @RequestParam int pageSize) {
        // Fetch volunteers associated with the event and filter by status
        PageInfo<Volunteer> volunteersByEventId = volunteerService.getVolunteersByEventId(eventId, status, pageNum, pageSize);

        // Set role names based on role ID
        for (Volunteer v : volunteersByEventId.getList()) {
            switch(v.getRoleId().intValue()) {
                case 1:
                    v.setRoleName("Event Coordinator");
                    break;
                case 2:
                    v.setRoleName("Event Welcome Desk");
                    break;
                case 3:
                    v.setRoleName("Athlete Registration Desk");
                    break;
                case 4:
                    v.setRoleName("Transport Operations");
                    break;
                case 5:
                    v.setRoleName("Event Greeter / Fan Experience");
                    break;
                case 6:
                    v.setRoleName("Entertainment Coordinator");
                    break;
                default:
                    v.setRoleName("Unknown Role");
                    break;
            }
        }
        return ResultVO.success(volunteersByEventId); // Return the list of volunteers with their roles
    }

    /**
     * Endpoint to submit a comment for a volunteer.
     * @param request The comment submission request containing volunteer ID, event ID, and the comment.
     * @return ResultVO containing a success or failure message.
     */
    @PostMapping("/submitComment")
    public ResultVO<String> submitComment(@RequestBody SubmitCommentRequest request) {
        try {
            // Submit the comment
            volunteerService.submitComment(request);
            // Fetch event details and add points awarded for the comment
            Event event = eventService.getEventById(request.getEventId());
            volunteerService.addPointsAwarded(request.getVolunteerId(), event.getPointsAwarded());
            return ResultVO.success("Comment submitted successfully");
        } catch (Exception e) {
            return ResultVO.failure("Failed to submit comment");
        }
    }

    /**
     * Endpoint to retrieve credentials for a volunteer by their ID.
     * @param credential The credential request object containing the volunteer ID.
     * @return ResultVO containing a list of credentials or null if not found.
     */
    @PostMapping("/getCredentialsByVolunteerId")
    public ResultVO<List<Credential>> getCredentialsByVolunteerId(@RequestBody Credential credential) {
        // Get volunteer by user ID and fetch associated credentials
        Volunteer volunteer = volunteerService.getVolunteerByUserId(credential.getVolunteerId());
        List<Credential> credentials = volunteerService.getCredentialsByVolunteerId(volunteer.getId());
        if (credentials != null && !credentials.isEmpty()) {
            return ResultVO.success(credentials); // Return the list of credentials if found
        } else {
            return ResultVO.success(null); // Return null if no credentials found
        }
    }

    /**
     * Endpoint to delete a credential.
     * @param credential The credential object containing the credential ID to be deleted.
     * @return ResultVO containing a success or failure message.
     */
    @PostMapping("/deleteCredential")
    public ResultVO<String> deleteCredential(@RequestBody Credential credential) {
        try {
            volunteerService.deleteCredentialById(credential.getId()); // Delete the credential by ID
            return ResultVO.success("Credential deleted successfully.");
        } catch (Exception e) {
            return ResultVO.failure("Failed to delete credential.");
        }
    }

    /**
     * Endpoint to update a credential.
     * @param credential The credential object containing updated credential details.
     * @return ResultVO containing a success or failure message.
     */
    @PostMapping("/updateCredential")
    public ResultVO<String> updateCredential(@RequestBody Credential credential) {
        try {
            volunteerService.updateCredential(credential); // Update the credential details
            return ResultVO.success("Credential updated successfully.");
        } catch (Exception e) {
            return ResultVO.failure("Failed to update credential.");
        }
    }

    /**
     * Endpoint to retrieve a volunteer by their user ID.
     * @param userId The ID of the user to fetch the volunteer.
     * @return ResultVO containing the volunteer details or a failure message.
     */
    @GetMapping("/getVolunteerByUserId/{userId}")
    public ResultVO<Volunteer> getVolunteerByUserId(@PathVariable Long userId) {
        try {
            Volunteer volunteer = volunteerService.getVolunteerByUserId(userId); // Fetch volunteer by user ID
            return ResultVO.success(volunteer);
        } catch (Exception e) {
            return ResultVO.failure("Failed to get volunteer by user ID.");
        }
    }
}
