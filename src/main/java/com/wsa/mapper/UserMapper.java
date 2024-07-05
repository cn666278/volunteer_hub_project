package com.wsa.mapper;

import com.wsa.model.Authority;
import com.wsa.model.Role;
import com.wsa.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAllUsers();

    User findByUsername(String username);

    List<Authority> findAuthoritiesByUsername(String username);

    Role findRoleByUserId(Long userId);
}

