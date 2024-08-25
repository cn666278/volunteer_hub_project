package com.wsa.lightweightmockmvcsecuritytests;

import com.wsa.service.CustomUserDetailsService;
import com.wsa.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class InsufficientPermissionSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    public void shouldReturnForbiddenForInsufficientPermissions() throws Exception {
        String username = "testuser";
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String jwt = jwtUtils.generateToken(userDetails);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        mockMvc.perform(get("/admin/getEchartData")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect((ResultMatcher) status().isForbidden());
    }

    private StatusAssertions status() {
        return null;
    }

}
