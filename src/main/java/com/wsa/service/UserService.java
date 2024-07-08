package com.wsa.service;

import com.wsa.exception.ResourceNotFoundException;
import com.wsa.mapper.UserMapper;
import com.wsa.model.Authority;
import com.wsa.model.User;
import com.wsa.model.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        logger.debug("Fetching all users from database.");
        List<User> users = userMapper.selectAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        logger.info("Number of users fetched: {}", users.size());
        return users;
    }

    public UserInfo getUserInfoByUsername(String username) {
        User user = userMapper.findByUsername(username);
        UserInfo userInfo = new UserInfo();
        if(user != null){
            userInfo.setUsername(user.getUsername());
            userInfo.setId(user.getId());
            userInfo.setLoginId(user.getLoginId());
            userInfo.setPhoto(user.getPhoto());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getPhone());
        }

        if (userInfo != null) {
            List<Authority> authoritiesByUsername = userMapper.findAuthoritiesByUsername(userInfo.getUsername());
            if ((authoritiesByUsername != null)){
                Authority authority = authoritiesByUsername.get(0);
                UserInfo.Role role = new UserInfo.Role();
                role.setRoleName(authority.getAuthority());
                role.setRoleId(String.valueOf(authority.getId()));
                userInfo.setRole(role);
            }
        }
        return userInfo;
    }

    public UserInfo getUserInfoByLoginId(String loginId) {
        User user = userMapper.findByLoginid(loginId);
        UserInfo userInfo = new UserInfo();
        if(user != null){
            userInfo.setUsername(user.getUsername());
            userInfo.setId(user.getId());
            userInfo.setLoginId(user.getLoginId());
            userInfo.setPhoto(user.getPhoto());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getPhone());
        }

        if (userInfo != null) {
            List<Authority> authoritiesByUsername = userMapper.findAuthoritiesByUsername(userInfo.getUsername());
            if ((authoritiesByUsername != null)){
                Authority authority = authoritiesByUsername.get(0);
                UserInfo.Role role = new UserInfo.Role();
                role.setRoleName(authority.getAuthority());
                role.setRoleId(String.valueOf(authority.getId()));
                userInfo.setRole(role);
            }
        }
        return userInfo;
    }

    public List<UserInfo> getUserListByRoleId(int roleId, int pageIndex, int pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        return userMapper.selectUsersByRoleId(roleId, offset, pageSize);
    }
}