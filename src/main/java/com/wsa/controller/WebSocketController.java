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
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        messageService.saveMessage(message);
        UserInfo userInfoById = userService.getUserInfoById(message.getSenderId());
        message.setUsername(userInfoById.getUsername());
        return message;
    }

    public void sendMessageToTopic(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}

