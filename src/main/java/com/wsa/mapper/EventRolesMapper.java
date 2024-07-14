package com.wsa.mapper;

import com.wsa.model.Event;
import com.wsa.model.EventRoles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRolesMapper {

void saveEventRoles(EventRoles eventRoles);
}