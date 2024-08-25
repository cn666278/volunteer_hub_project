package com.wsa.fullcontainermvcsecuritytests;

import com.wsa.service.CustomUserDetailsService;
import com.wsa.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testPublicEndpointAccess() throws Exception {
        // Test that public endpoints are accessible without authentication
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/event/getLatestEvents"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpointAccessDeniedWithoutAuthentication() throws Exception {
        // Test that accessing protected endpoints without authentication is denied
        mockMvc.perform(MockMvcRequestBuilders.get("/secure-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testProtectedEndpointAccessWithValidJwtToken() throws Exception {
        // Test accessing a protected endpoint with a valid JWT token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("validUser");
        String token = jwtUtils.generateToken(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/secure-endpoint")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpointAccessWithInvalidJwtToken() throws Exception {
        // Test accessing a protected endpoint with an invalid JWT token
        String invalidToken = "invalid.jwt.token";

        mockMvc.perform(MockMvcRequestBuilders.get("/secure-endpoint")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized());
    }
}
