package com.wsa.controller;

import com.wsa.model.EventRequest;
import com.wsa.model.Message;
import com.wsa.model.ResultVO;
import com.wsa.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/getMessagesByEventId")
    public ResultVO<List<Message>> getMessagesByEventId(@RequestBody EventRequest event) {
        return ResultVO.success(messageService.getMessagesByEventId(event.getEventId()));
    }

    @PostMapping("/deleteMessage")
    public ResultVO<String> deleteMessage(@RequestBody Message message) {
        messageService.deleteMessageById(message.getId());
        return ResultVO.success("Message deleted successfully.");
    }
}
