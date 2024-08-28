package com.wsa.controller;

import com.wsa.model.EventRequest;
import com.wsa.model.Message;
import com.wsa.model.ResultVO;
import com.wsa.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages") // Base URL for all message-related endpoints
public class MessageController {

    @Autowired
    private MessageService messageService; // Service for handling message-related operations

    /**
     * Endpoint to get messages by event ID.
     * @param event The event request containing the event ID.
     * @return ResultVO containing a list of messages associated with the given event ID.
     */
    @PostMapping("/getMessagesByEventId")
    public ResultVO<List<Message>> getMessagesByEventId(@RequestBody EventRequest event) {
        // Retrieve messages associated with the specified event ID
        return ResultVO.success(messageService.getMessagesByEventId(event.getEventId()));
    }

    /**
     * Endpoint to delete a message by its ID.
     * @param message The message object containing the message ID to be deleted.
     * @return ResultVO with a success message if deletion is successful.
     */
    @PostMapping("/deleteMessage")
    public ResultVO<String> deleteMessage(@RequestBody Message message) {
        messageService.deleteMessageById(message.getId()); // Delete the message using its ID
        return ResultVO.success("Message deleted successfully.");
    }
}
