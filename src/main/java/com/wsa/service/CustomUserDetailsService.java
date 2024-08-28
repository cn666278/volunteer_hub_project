package com.wsa.service;

import com.wsa.mapper.UserMapper;
import com.wsa.model.Authority;
import com.wsa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom implementation of UserDetailsService to provide user authentication and authorization details.
 * This service fetches user information and their roles/authorities from the database.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper; // Mapper for database interactions related to user data

    /**
     * Loads a user based on the provided username.
     * Retrieves user details and authorities (roles) from the database to authenticate and authorize.
     * @param username The username of the user to be loaded.
     * @return UserDetails object containing user credentials and authorities.
     * @throws UsernameNotFoundException if the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from the database using the username
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username); // Throw exception if user is not found
        }

        // Retrieve authorities (roles) associated with the user
        List<Authority> authorities = userMapper.findAuthoritiesByUserId(user.getId());

        // Convert the authorities into GrantedAuthority objects required by Spring Security
        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());

        // Return a UserDetails object containing user credentials and granted authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(), // Check if the user is enabled
                true, // Account non-expired
                true, // Credentials non-expired
                true, // Account non-locked
                grantedAuthorities // List of authorities granted to the user
        );
    }
}
