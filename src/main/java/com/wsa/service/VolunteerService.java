package com.wsa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsa.mapper.CredentialMapper;
import com.wsa.mapper.VolunteerMapper;
import com.wsa.model.Credential;
import com.wsa.model.SubmitCommentRequest;
import com.wsa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    public PageInfo<Volunteer> getVolunteersByEventId(Long eventId, String status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Volunteer> volunteers = volunteerMapper.getVolunteersByEventId(eventId, status);
        return new PageInfo<>(volunteers);
    }

    public void submitComment(SubmitCommentRequest request) {
        volunteerMapper.insertVolunteerRating(request);
    }

    public Volunteer getVolunteerByUserId(Long userId) {
        return volunteerMapper.getVolunteerByUserId(userId);
    }

    @Autowired
    private CredentialMapper credentialMapper;

    public List<Credential> getCredentialsByVolunteerId(Long volunteerId) {
        // 调用Mapper方法获取凭证数据
        return credentialMapper.findCredentialsByVolunteerId(volunteerId);
    }

    public void deleteCredentialById(Long id) {
        credentialMapper.deleteCredentialById(id);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public void addPointsAwarded(Long volunteerId, Integer pointsAwarded) {
        volunteerMapper.addPointsAwarded(volunteerId, pointsAwarded);
    }

    public void addCredential(Long id, String filename, String s) {
        volunteerMapper.addCredential(id,filename,s);
    }
}

