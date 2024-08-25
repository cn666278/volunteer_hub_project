package com.wsa.lightweightmockmvctests;

import com.wsa.controller.FileUploadController;
import com.wsa.model.FileModel;
import com.wsa.model.Volunteer;
import com.wsa.service.FileService;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();
    }

    @Test
    public void testUploadFile_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        doAnswer(invocation -> {
            FileModel fileModel = invocation.getArgument(0);
            fileModel.setId(1L);
            return null;  // Since saveFile is void, we return null here
        }).when(fileService).saveFile(any(FileModel.class));

        // When & Then
        mockMvc.perform(multipart("/upload")
                        .file(file)
                        .param("filename", "testfile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("File uploaded successfully with ID: 1"));
    }

    @Test
    public void testUploadFileForVolunteer_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);

        when(volunteerService.getVolunteerByUserId(1L)).thenReturn(volunteer);
        doAnswer(invocation -> {
            FileModel fileModel = invocation.getArgument(0);
            fileModel.setId(1L);
            return null;  // Since saveFile is void, we return null here
        }).when(fileService).saveFile(any(FileModel.class));

        // When & Then
        mockMvc.perform(multipart("/uploadForVolunteer")
                        .file(file)
                        .param("filename", "testfile")
                        .param("volunteerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("File uploaded successfully with ID: 1"));
    }

    @Test
    public void testUploadAvatorForVolunteer_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "avatar.png", MediaType.IMAGE_PNG_VALUE, "image content".getBytes());

        doAnswer(invocation -> {
            FileModel fileModel = invocation.getArgument(0);
            fileModel.setId(1L);
            return null;  // Since saveFile is void, we return null here
        }).when(fileService).saveFile(any(FileModel.class));

        // When & Then
        mockMvc.perform(multipart("/uploadAvatorForVolunteer")
                        .file(file)
                        .param("filename", "avatar")
                        .param("volunteerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("File uploaded successfully with ID: 1"));
    }

    @Test
    public void testGetFile_Success() throws Exception {
        // Given
        FileModel fileModel = new FileModel();
        fileModel.setId(1L);
        fileModel.setData("file content".getBytes());
        when(fileService.getFileById(1L)).thenReturn(fileModel);

        // When & Then
        mockMvc.perform(get("/files/1")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Base64Utils.encodeToString("file content".getBytes())));
    }

    @Test
    public void testGetFile_NotFound() throws Exception {
        // Given
        when(fileService.getFileById(1L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/files/1")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }
}
