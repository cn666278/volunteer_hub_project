package com.wsa.service;

import com.wsa.mapper.OrganizerMapper;
import com.wsa.model.Organizer;
import com.wsa.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing organizer-related operations.
 * This service interacts with the data access layer (OrganizerMapper) to handle organizer data retrieval and manipulation.
 */
@Service
public class OrganizerService {

    @Autowired
    private OrganizerMapper organizerMapper; // Mapper for database interactions related to organizers

    /**
     * Retrieves user information associated with a given organizer ID.
     * @param organizerId The ID of the organizer.
     * @return UserInfo object containing user information if found, otherwise null.
     */
    public UserInfo getUserInfoByOrganizerId(Long organizerId) {
        // Check if the organizer ID is provided
        if (organizerId != null) {
            return organizerMapper.getUserInfoById(organizerId); // Fetch user information by organizer ID
        } else {
            return null; // Return null if organizer ID is not provided
        }
    }

    /**
     * Retrieves a paginated list of organizers.
     * @param current The current page number.
     * @param pageSize The number of records per page.
     * @return List of organizers for the specified page.
     */
    public List<Organizer> getOrganizersByPage(int current, int pageSize) {
        int offset = (current - 1) * pageSize; // Calculate offset for pagination
        return organizerMapper.getOrganizersByPage(offset, pageSize); // Fetch organizers by page
    }

    /**
     * Retrieves an organizer by its ID.
     * @param organizerId The ID of the organizer.
     * @return Organizer object containing organizer details.
     */
    public Organizer getOrganizersById(Long organizerId) {
        return organizerMapper.getOrganizersById(organizerId); // Fetch organizer by ID
    }
}
