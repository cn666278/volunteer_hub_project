package com.wsa.lightweightmockmvcsecuritytests;

import com.wsa.service.CustomUserDetailsService;
import com.wsa.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtRequestFilterSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    public void shouldReturnForbiddenForInvalidJwt() throws Exception {
        String invalidJwt = "invalid.jwt.token";

        mockMvc.perform(get("/admin/getEchartData")
                        .header("Authorization", "Bearer " + invalidJwt))
                .andExpect(status().isForbidden());
    }
}
