package com.wsa.mapper;

import com.wsa.model.Organizer;
import com.wsa.model.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizerMapper {

//    @Select("SELECT user_id FROM organizers WHERE id = #{organizerId}")
    Long findUserIdByOrganizerId(Long organizerId);

//    @Select("SELECT * FROM users WHERE id = #{userId}")
    UserInfo getUserInfoById(Long userId);

//    @Select("SELECT * FROM organizers LIMIT #{offset}, #{pageSize}")
    List<Organizer> getOrganizersByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
