package com.wsa.SimpleUnitTests;

import com.wsa.mapper.AuthorityMapper;
import com.wsa.service.AuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verifyNoInteractions;

public class AuthorityServiceTest {

    @Mock
    private AuthorityMapper authorityMapper;

    @InjectMocks
    private AuthorityService authorityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAuthorityServiceInitialization() {
        // Since there are no methods in AuthorityService, we'll just verify no interactions with mocks
        verifyNoInteractions(authorityMapper);
    }
}
