package com.wsa.itemmockmvctests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsa.model.Organizer;
import com.wsa.model.OrganizerRequest;
import com.wsa.model.ResultVO;
import com.wsa.model.UserInfo;
import com.wsa.service.OrganizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizerService organizerService;

    @Test
    public void shouldReturnUserInfoByOrganizerId() throws Exception {
        // 准备测试数据
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("organizer_user");

        OrganizerRequest request = new OrganizerRequest();
        request.setOrganizerId(1L);

        when(organizerService.getUserInfoByOrganizerId(1L)).thenReturn(userInfo);

        // 执行测试
        mockMvc.perform(post("/organizer/getUserByOrganizerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("organizer_user"));
    }

    @Test
    public void shouldReturnAllOrganizers() throws Exception {
        // 准备测试数据
        Organizer organizer1 = new Organizer();
        organizer1.setId(1L);
        organizer1.setOrganizationName("Org 1");

        Organizer organizer2 = new Organizer();
        organizer2.setId(2L);
        organizer2.setOrganizationName("Org 2");

        List<Organizer> organizers = Arrays.asList(organizer1, organizer2);

        when(organizerService.getOrganizersByPage(1, 10)).thenReturn(organizers);

        // 执行测试
        mockMvc.perform(get("/organizer/getAllOrganizers")
                        .param("current", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].organizationName").value("Org 1"))
                .andExpect(jsonPath("$.data[1].organizationName").value("Org 2"));
    }
}
