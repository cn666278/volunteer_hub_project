package com.wsa.service;

import com.wsa.mapper.MessageMapper;
import com.wsa.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;


    public void saveMessage(Message message) {
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        messageMapper.save(message);
    }

    public List<Message> getMessagesByEventId(Long eventId) {
        return messageMapper.getMessagesByEventId(eventId);
    }

    public void deleteMessageById(Long messageId) {
        messageMapper.deleteMessageById(messageId);
    }
}
