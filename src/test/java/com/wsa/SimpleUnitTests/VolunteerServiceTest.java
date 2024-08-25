package com.wsa.SimpleUnitTests;

import com.github.pagehelper.PageInfo;
import com.wsa.mapper.CredentialMapper;
import com.wsa.mapper.VolunteerMapper;
import com.wsa.model.Credential;
import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import com.wsa.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VolunteerServiceTest {

    @Mock
    private VolunteerMapper volunteerMapper;

    @Mock
    private CredentialMapper credentialMapper;

    @InjectMocks
    private VolunteerService volunteerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetVolunteersByEventId() {
        // Arrange
        Long eventId = 1L;
        String status = "active";
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(2L);

        when(volunteerMapper.getVolunteersByEventId(eventId, status)).thenReturn(Arrays.asList(volunteer1, volunteer2));

        // Act
        PageInfo<Volunteer> result = volunteerService.getVolunteersByEventId(eventId, status, 1, 10);

        // Assert
        assertThat(result.getList()).hasSize(2);
        verify(volunteerMapper).getVolunteersByEventId(eventId, status);
    }

    @Test
    void testSubmitComment() {
        // Arrange
        SubmitCommentRequest request = new SubmitCommentRequest();
        request.setVolunteerId(1L);
        request.setComment("Great job!");

        // Act
        volunteerService.submitComment(request);

        // Assert
        verify(volunteerMapper).insertVolunteerRating(request);
    }

    @Test
    void testGetVolunteerByUserId() {
        // Arrange
        Long userId = 1L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(userId);

        when(volunteerMapper.getVolunteerByUserId(userId)).thenReturn(volunteer);

        // Act
        Volunteer result = volunteerService.getVolunteerByUserId(userId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        verify(volunteerMapper).getVolunteerByUserId(userId);
    }

    @Test
    void testGetCredentialsByVolunteerId() {
        // Arrange
        Long volunteerId = 1L;
        Credential credential1 = new Credential();
        credential1.setId(1L);
        Credential credential2 = new Credential();
        credential2.setId(2L);

        when(credentialMapper.findCredentialsByVolunteerId(volunteerId)).thenReturn(Arrays.asList(credential1, credential2));

        // Act
        List<Credential> credentials = volunteerService.getCredentialsByVolunteerId(volunteerId);

        // Assert
        assertThat(credentials).hasSize(2);
        verify(credentialMapper).findCredentialsByVolunteerId(volunteerId);
    }

    @Test
    void testDeleteCredentialById() {
        // Arrange
        Long credentialId = 1L;

        // Act
        volunteerService.deleteCredentialById(credentialId);

        // Assert
        verify(credentialMapper).deleteCredentialById(credentialId);
    }

    @Test
    void testAddPointsAwarded() {
        // Arrange
        Long volunteerId = 1L;
        Integer pointsAwarded = 10;

        // Act
        volunteerService.addPointsAwarded(volunteerId, pointsAwarded);

        // Assert
        verify(volunteerMapper).addPointsAwarded(volunteerId, pointsAwarded);
    }

    @Test
    void testAddCredential() {
        // Arrange
        Long volunteerId = 1L;
        String filename = "certification.pdf";
        String description = "Certification for participation";

        // Act
        volunteerService.addCredential(volunteerId, filename, description);

        // Assert
        verify(volunteerMapper).addCredential(volunteerId, filename, description);
    }
}

