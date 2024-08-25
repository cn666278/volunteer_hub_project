package com.wsa.lightweightmockmvctests;

import com.wsa.controller.MessageController;
import com.wsa.model.EventRequest;
import com.wsa.model.Message;
import com.wsa.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    public void testGetMessagesByEventId_Success() throws Exception {
        // Given
        List<Message> messages = new ArrayList<>();
        Message message1 = new Message();
        message1.setId(1L);
        message1.setContent("Message 1");
        messages.add(message1);

        when(messageService.getMessagesByEventId(anyLong())).thenReturn(messages);

        // When & Then
        mockMvc.perform(post("/messages/getMessagesByEventId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].content").value("Message 1"));
    }

    @Test
    public void testDeleteMessage_Success() throws Exception {
        // Given
        doNothing().when(messageService).deleteMessageById(anyLong());

        // When & Then
        mockMvc.perform(post("/messages/deleteMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Message deleted successfully."));
    }
}
