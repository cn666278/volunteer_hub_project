package com.wsa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsa.mapper.CredentialMapper;
import com.wsa.mapper.VolunteerMapper;
import com.wsa.model.Credential;
import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing volunteer-related operations.
 * This service interacts with the data access layer (VolunteerMapper and CredentialMapper)
 * to handle volunteer data, comments, credentials, and points management.
 */
@Service
public class VolunteerService {

    @Autowired
    private VolunteerMapper volunteerMapper; // Mapper for database interactions related to volunteers

    @Autowired
    private CredentialMapper credentialMapper; // Mapper for database interactions related to credentials

    /**
     * Retrieves a paginated list of volunteers by event ID and status.
     * @param eventId The ID of the event.
     * @param status The status of the volunteers (e.g., "active", "inactive").
     * @param pageNum The current page number.
     * @param pageSize The number of records per page.
     * @return PageInfo object containing paginated list of volunteers.
     */
    public PageInfo<Volunteer> getVolunteersByEventId(Long eventId, String status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); // Set pagination parameters
        List<Volunteer> volunteers = volunteerMapper.getVolunteersByEventId(eventId, status); // Fetch volunteers by event ID and status
        return new PageInfo<>(volunteers);
    }

    /**
     * Submits a comment or rating for a volunteer.
     * @param request The request object containing comment details.
     */
    public void submitComment(SubmitCommentRequest request) {
        volunteerMapper.insertVolunteerRating(request); // Insert volunteer rating/comment into the database
    }

    /**
     * Retrieves a volunteer by their user ID.
     * @param userId The user ID associated with the volunteer.
     * @return Volunteer object containing volunteer details.
     */
    public Volunteer getVolunteerByUserId(Long userId) {
        return volunteerMapper.getVolunteerByUserId(userId); // Fetch volunteer by user ID
    }

    /**
     * Retrieves a list of credentials for a specific volunteer.
     * @param volunteerId The ID of the volunteer.
     * @return List of Credential objects associated with the volunteer.
     */
    public List<Credential> getCredentialsByVolunteerId(Long volunteerId) {
        // Call the Mapper method to get credential data
        return credentialMapper.findCredentialsByVolunteerId(volunteerId);
    }

    /**
     * Deletes a credential by its ID.
     * @param id The ID of the credential to be deleted.
     */
    public void deleteCredentialById(Long id) {
        credentialMapper.deleteCredentialById(id); // Delete credential by ID
    }

    /**
     * Updates an existing credential.
     * @param credential The Credential object containing updated details.
     */
    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential); // Update credential details in the database
    }

    /**
     * Adds points awarded to a volunteer.
     * @param volunteerId The ID of the volunteer.
     * @param pointsAwarded The number of points to be awarded.
     */
    public void addPointsAwarded(Long volunteerId, Integer pointsAwarded) {
        volunteerMapper.addPointsAwarded(volunteerId, pointsAwarded); // Update volunteer points in the database
    }

    /**
     * Adds a new credential for a volunteer.
     * @param id The ID of the volunteer.
     * @param filename The name of the file representing the credential.
     * @param path The path or URL where the credential is stored.
     */
    public void addCredential(Long id, String filename, String path) {
        volunteerMapper.addCredential(id, filename, path); // Insert new credential into the database
    }
}
