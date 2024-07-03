package com.wsa.service;

import com.wsa.exception.ResourceNotFoundException;
import com.wsa.mapper.UserMapper;
import com.wsa.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        logger.debug("Fetching all users from database.");
        List<User> users = userMapper.selectAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        logger.info("Number of users fetched: {}", users.size());
        return users;
    }

}