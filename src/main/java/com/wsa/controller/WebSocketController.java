package com.wsa.controller;

import com.wsa.model.Message;
import com.wsa.model.UserInfo;
import com.wsa.service.MessageService;
import com.wsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Template for sending messages to a specific destination

    @Autowired
    private MessageService messageService; // Service for handling message-related operations

    @Autowired
    private UserService userService; // Service for managing user-related operations

    /**
     * Handles incoming messages sent to the "/sendMessage" endpoint.
     * Saves the message and broadcasts it to all subscribers of the "/topic/messages" topic.
     * @param message The message object containing the content and sender information.
     * @return The message object with the sender's username populated.
     */
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        messageService.saveMessage(message); // Save the message to the database

        // Retrieve the sender's user information and set the username in the message
        UserInfo userInfoById = userService.getUserInfoById(message.getSenderId());
        message.setUsername(userInfoById.getUsername());

        // Return the message object, which will be broadcasted to all subscribers of "/topic/messages"
        return message;
    }

    /**
     * Sends a message to all subscribers of the "/topic/messages" topic.
     * This method can be called programmatically to broadcast messages.
     * @param message The message object to be sent to the topic.
     */
    public void sendMessageToTopic(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message); // Send message to the specified topic
    }
}
