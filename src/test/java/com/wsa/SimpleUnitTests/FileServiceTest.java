package com.wsa.SimpleUnitTests;

import com.wsa.mapper.FileMapper;
import com.wsa.model.FileModel;
import com.wsa.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileServiceTest {

    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveFile() {
        // Arrange
        FileModel fileModel = new FileModel();
        fileModel.setId(1L);
        fileModel.setFilename("testfile.txt");

        // Act
        fileService.saveFile(fileModel);

        // Assert
        verify(fileMapper).insert(fileModel);
    }

    @Test
    void testGetFileById() {
        // Arrange
        Long fileId = 1L;
        FileModel fileModel = new FileModel();
        fileModel.setId(fileId);
        fileModel.setFilename("testfile.txt");

        when(fileMapper.findById(fileId)).thenReturn(fileModel);

        // Act
        FileModel result = fileService.getFileById(fileId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFilename()).isEqualTo("testfile.txt");
        verify(fileMapper).findById(fileId);
    }
}
