package com.wsa.mapper;

import com.wsa.model.EventRegistrations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventRegistrationsMapper {

    void updateStatusById(@Param("id") Long id, @Param("status") String status);
}
