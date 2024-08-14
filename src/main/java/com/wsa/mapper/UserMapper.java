package com.wsa.mapper;

import com.wsa.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAllUsers();

    User findByUsername(String username);

    User findById(Long id);

    List<Authority> findAuthoritiesByUserId(Long userId);

    Role findRoleByUserId(Long userId);

    List<UserInfo> selectUsersByRoleId(@Param("roleId") int roleId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(UserReq request);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(@Param("id") Long id);


    void updateUserProfile(User user);

    @Select("SELECT vr.*, e.title AS eventName FROM volunteerratings vr " +
            "JOIN events e ON vr.eventId = e.id " +
            "WHERE vr.volunteerId = #{volunteerId}")
    List<VolunteerRating> findRatingsByVolunteerId(@Param("volunteerId") Long volunteerId);

    @Select("SELECT * FROM volunteerinfo WHERE volunteerId = #{volunteerId}")
    List<VolunteerInfo> findVolunteerInfoByVolunteerId(@Param("volunteerId") Long volunteerId);



    void updateLoginTime(String username);
}

