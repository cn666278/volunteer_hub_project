package com.wsa.lightweightmockmvctests;

import com.wsa.controller.WebSocketController;
import com.wsa.model.Message;
import com.wsa.model.UserInfo;
import com.wsa.service.MessageService;
import com.wsa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WebSocketControllerTest {

    @InjectMocks
    private WebSocketController webSocketController;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() throws Exception {
        // Given
        Message message = new Message();
        message.setSenderId(1L);
        message.setContent("Hello");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("JohnDoe");

        when(userService.getUserInfoById(1L)).thenReturn(userInfo);

        // When
        Message returnedMessage = webSocketController.sendMessage(message);

        // Then
        verify(messageService, times(1)).saveMessage(message);
        assertEquals("JohnDoe", returnedMessage.getUsername());
        assertEquals("Hello", returnedMessage.getContent());
    }

    @Test
    public void testSendMessageToTopic() {
        // Given
        Message message = new Message();
        message.setContent("Test message");

        // When
        webSocketController.sendMessageToTopic(message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend("/topic/messages", message);
    }
}
