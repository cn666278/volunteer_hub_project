package com.wsa.SimpleUnitTests;

import com.wsa.mapper.OrganizerMapper;
import com.wsa.model.Organizer;
import com.wsa.model.UserInfo;
import com.wsa.service.OrganizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrganizerServiceTest {

    @Mock
    private OrganizerMapper organizerMapper;

    @InjectMocks
    private OrganizerService organizerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserInfoByOrganizerId() {
        // Arrange
        Long organizerId = 1L;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(organizerId);
        userInfo.setUsername("testuser");

        when(organizerMapper.getUserInfoById(organizerId)).thenReturn(userInfo);

        // Act
        UserInfo result = organizerService.getUserInfoByOrganizerId(organizerId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(organizerMapper).getUserInfoById(organizerId);
    }

    @Test
    void testGetOrganizersByPage() {
        // Arrange
        int currentPage = 1;
        int pageSize = 10;
        Organizer organizer1 = new Organizer();
        organizer1.setId(1L);
        organizer1.setOrganizationName("Organizer 1");

        Organizer organizer2 = new Organizer();
        organizer2.setId(2L);
        organizer2.setOrganizationName("Organizer 2");

        when(organizerMapper.getOrganizersByPage(0, pageSize)).thenReturn(Arrays.asList(organizer1, organizer2));

        // Act
        List<Organizer> organizers = organizerService.getOrganizersByPage(currentPage, pageSize);

        // Assert
        assertThat(organizers).hasSize(2);
        verify(organizerMapper).getOrganizersByPage(0, pageSize);
    }

    @Test
    void testGetOrganizersById() {
        // Arrange
        Long organizerId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(organizerId);
        organizer.setOrganizationName("Organizer 1");

        when(organizerMapper.getOrganizersById(organizerId)).thenReturn(organizer);

        // Act
        Organizer result = organizerService.getOrganizersById(organizerId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getOrganizationName()).isEqualTo("Organizer 1");
        verify(organizerMapper).getOrganizersById(organizerId);
    }
}
