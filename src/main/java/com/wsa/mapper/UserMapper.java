package com.wsa.mapper;

import com.wsa.model.Authority;
import com.wsa.model.Role;
import com.wsa.model.User;
import com.wsa.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAllUsers();

    User findByUsername(String username);

    User findByLoginid(String loginId);

    List<Authority> findAuthoritiesByUsername(String username);

    Role findRoleByUserId(Long userId);

    List<UserInfo> selectUsersByRoleId(@Param("roleId") int roleId, @Param("offset") int offset, @Param("pageSize") int pageSize);}

