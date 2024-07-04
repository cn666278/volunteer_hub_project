package com.wsa.controller;

import com.wsa.model.JwtRequest;
import com.wsa.model.JwtResponse;
import com.wsa.model.UserInfo;
import com.wsa.service.CustomUserDetailsService;
import com.wsa.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/api/admin/getUserInfo")
    public UserInfo createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);
        UserInfo userInfo = new UserInfo();
        userInfo.setToken(jwt);
        userInfo.setPhone("100888");
        userInfo.setId(2L);
        userInfo.setPhoto("https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png");
        userInfo.setEmail("123@gmail.com");
        userInfo.setUsername("organizer");
        userInfo.setLoginId("organizer");
        UserInfo.Role role = new UserInfo.Role();
        role.setRoleId("2");
        role.setRoleName("organizer");
        userInfo.setRole(role);
        return userInfo;
    }
}