package com.wsa.service;

import com.wsa.mapper.AuthorityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing authority-related operations.
 * This class interacts with the data access layer (AuthorityMapper) to perform CRUD operations on authority data.
 */
@Service
public class AuthorityService {

    // Logger instance for logging important events and errors
    private static final Logger logger = LogManager.getLogger(AuthorityService.class);

    @Autowired
    private AuthorityMapper authorityMapper; // Mapper for database interactions related to authorities

    // Future methods to manage authorities (e.g., adding, updating, deleting) would go here.
}
