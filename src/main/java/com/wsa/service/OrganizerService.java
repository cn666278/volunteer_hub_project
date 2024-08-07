package com.wsa.service;

import com.wsa.mapper.OrganizerMapper;
import com.wsa.model.Organizer;
import com.wsa.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerMapper organizerMapper;

    public UserInfo getUserInfoByOrganizerId(Long organizerId) {
        Long userId = organizerMapper.findUserIdByOrganizerId(organizerId);
        if (userId != null) {
            return organizerMapper.getUserInfoById(userId);
        } else {
            return null;
        }
    }

    public List<Organizer> getOrganizersByPage(int current, int pageSize) {
        int offset = (current - 1) * pageSize;
        return organizerMapper.getOrganizersByPage(offset, pageSize);
    }
}
