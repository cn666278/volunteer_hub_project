package com.wsa.lightweightmockmvcsecuritytests;

import com.wsa.service.CustomUserDetailsService;
import com.wsa.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void shouldAuthenticateAndReturnJwtToken() throws Exception {
        String username = "testuser";
        String password = "testpassword";

        // 模拟 UserDetailsService 和 AuthenticationManager 的行为
        UserDetails userDetails = new User(username, password, new ArrayList<>());
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));

        String jsonRequest = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

        mockMvc.perform((RequestBuilder) post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(jsonRequest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.token").exists());
    }
}
