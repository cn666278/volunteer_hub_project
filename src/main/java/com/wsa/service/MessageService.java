package com.wsa.service;

import com.wsa.mapper.MessageMapper;
import com.wsa.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing message-related operations.
 * This service interacts with the data access layer (MessageMapper) to handle message storage, retrieval, and deletion.
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper; // Mapper for database interactions related to messages

    /**
     * Saves a message to the database.
     * If the message does not have a timestamp, it sets the current timestamp.
     * @param message The message object containing the message details to be saved.
     */
    public void saveMessage(Message message) {
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now()); // Set the current timestamp if not provided
        }
        messageMapper.save(message); // Save the message to the database
    }

    /**
     * Retrieves a list of messages associated with a specific event ID.
     * @param eventId The ID of the event for which to retrieve messages.
     * @return A list of messages associated with the specified event ID.
     */
    public List<Message> getMessagesByEventId(Long eventId) {
        return messageMapper.getMessagesByEventId(eventId); // Fetch messages by event ID
    }

    /**
     * Deletes a message from the database by its ID.
     * @param messageId The ID of the message to be deleted.
     */
    public void deleteMessageById(Long messageId) {
        messageMapper.deleteMessageById(messageId); // Delete the message by its ID
    }
}
