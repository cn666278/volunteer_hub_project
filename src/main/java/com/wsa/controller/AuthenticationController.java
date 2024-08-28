package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.CustomUserDetailsService;
import com.wsa.service.UserService;
import com.wsa.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // Enables Cross-Origin Resource Sharing (CORS) for this controller
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Manages authentication processes

    @Autowired
    private JwtUtils jwtUtils; // Utility class for generating and validating JWT tokens

    @Autowired
    private CustomUserDetailsService userDetailsService; // Service to load user-specific data

    @Autowired
    private UserService userService; // Service for user-related operations

    /**
     * Endpoint for user login and JWT token generation.
     * @param authenticationRequest Contains the username and password for authentication.
     * @return ResultVO<LoginResult> containing the JWT token and success message.
     * @throws Exception if authentication fails.
     */
    @PostMapping("/login")
    public ResultVO<LoginResult> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        // Authenticate the user based on the provided username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        // Load user details for generating JWT token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Generate JWT token using user details
        final String jwt = jwtUtils.generateToken(userDetails);

        // Prepare the response containing the JWT token and success message
        LoginResult loginResult = new LoginResult();

        // Update the login time for the user
        userService.updateLoginTime(authenticationRequest);

        loginResult.setMessage("success"); // Indicate successful login
        loginResult.setToken(jwt); // Set the generated JWT token in the response

        return ResultVO.success(loginResult); // Return the success result with login details
    }
}
