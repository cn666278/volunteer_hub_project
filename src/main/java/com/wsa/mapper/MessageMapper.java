package com.wsa.mapper;

import com.wsa.model.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    void save(Message message);

    List<Message> getMessagesByEventId(Long eventId);

    void deleteMessageById(Long id);
}
