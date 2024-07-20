package com.wsa.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsa.mapper.VolunteerMapper;
import com.wsa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    public PageInfo<Volunteer> getVolunteersByEventId(Long eventId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Volunteer> volunteers = volunteerMapper.getVolunteersByEventId(eventId);
        return new PageInfo<>(volunteers);
    }
}

