package com.wsa.SimpleUnitTests;

import com.wsa.mapper.MessageMapper;
import com.wsa.model.Message;
import com.wsa.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageServiceTest {

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveMessageWithTimestamp() {
        // Arrange
        Message message = new Message();
        message.setContent("Test message");

        // Act
        messageService.saveMessage(message);

        // Assert
        assertThat(message.getTimestamp()).isNotNull();
        verify(messageMapper).save(message);
    }

    @Test
    void testGetMessagesByEventId() {
        // Arrange
        Long eventId = 1L;
        Message message1 = new Message();
        message1.setId(1L);
        message1.setContent("Message 1");

        Message message2 = new Message();
        message2.setId(2L);
        message2.setContent("Message 2");

        when(messageMapper.getMessagesByEventId(eventId)).thenReturn(Arrays.asList(message1, message2));

        // Act
        List<Message> messages = messageService.getMessagesByEventId(eventId);

        // Assert
        assertThat(messages).hasSize(2);
        verify(messageMapper).getMessagesByEventId(eventId);
    }

    @Test
    void testDeleteMessageById() {
        // Arrange
        Long messageId = 1L;

        // Act
        messageService.deleteMessageById(messageId);

        // Assert
        verify(messageMapper).deleteMessageById(messageId);
    }
}
