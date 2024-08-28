package com.wsa.util;

import com.wsa.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that intercepts each HTTP request to validate JWT tokens for user authentication.
 * This filter ensures that the JWT token provided in the request header is valid
 * and sets up the security context for authenticated users.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Service to load user-specific data

    @Autowired
    private JwtUtils jwtUtils; // Utility class for handling JWT operations (extraction, validation)

    /**
     * Filter method to validate the JWT token and set the authentication context.
     * This method is executed once per request to check if the user is authenticated
     * based on the JWT token provided in the request header.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @param chain    The filter chain to pass the request/response to the next filter.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Retrieve the "Authorization" header from the HTTP request
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the header contains a Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extract the token part after "Bearer "
            username = jwtUtils.extractUsername(jwt); // Extract username from the token
        }

        // Check if the username is not null and the user is not authenticated in the security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details using the username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate the token using the user details
            if (jwtUtils.validateToken(jwt, userDetails)) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
