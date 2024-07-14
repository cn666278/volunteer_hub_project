package com.wsa.mapper;

import com.wsa.model.EventFacilities;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventFacilitiesMapper {

    void saveEventFacilities(EventFacilities eventFacilities);
}