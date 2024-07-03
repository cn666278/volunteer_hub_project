package com.wsa.controller;

import com.wsa.exception.ResourceNotFoundException;
import com.wsa.model.User;
import com.wsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

}