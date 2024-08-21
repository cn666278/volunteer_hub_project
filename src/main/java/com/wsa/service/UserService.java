package com.wsa.service;

import com.wsa.exception.ResourceNotFoundException;
import com.wsa.mapper.*;
import com.wsa.model.*;
import com.wsa.util.PasswordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private OrganizerMapper organizerMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;
    @Autowired
    private RoleMapper roleMapper;
    public List<UserInfo> getAllUsers() {
        List<UserInfo> userInfos = new ArrayList<>();
        List<User> users = userMapper.selectAllUsers();
        if(users != null){
            for (User user:users
                 ) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(user.getUsername());
                userInfo.setId(user.getId());
                userInfo.setLoginId(user.getLoginId());
                userInfo.setPhoto(user.getPhoto());
                userInfo.setEmail(user.getEmail());
                userInfo.setPhone(user.getPhone());
                userInfo.setLastLoginTime(user.getLastLoginTime());
                findAuthoritiesByUserId(userInfo);
                userInfos.add(userInfo);
            }
        }
        if (userInfos.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return userInfos;
    }

    private void findAuthoritiesByUserId(UserInfo userInfo) {
        List<Authority> authoritiesByUserId = userMapper.findAuthoritiesByUserId(userInfo.getId());
        if (authoritiesByUserId != null){
            Authority authority = authoritiesByUserId.get(0);
            UserInfo.Role role = new UserInfo.Role();
            role.setRoleName(authority.getAuthority());
            role.setRoleId(String.valueOf(authority.getRoleId()));
            userInfo.setRole(role);
        }
    }

    public UserInfo getUserInfoByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return getUserInfo(user);
    }

    public UserInfo getUserInfoById(Long id) {
        User user = userMapper.findById(id);
        return getUserInfo(user);
    }

    private UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        if(user != null){
            userInfo.setUsername(user.getUsername());
            userInfo.setId(user.getId());
            userInfo.setLoginId(user.getLoginId());
            userInfo.setPhoto(user.getPhoto());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getPhone());
            userInfo.setLastLoginTime(user.getLastLoginTime());
        }

        if (userInfo != null) {
            findAuthoritiesByUserId(userInfo);
        }
        return userInfo;
    }

    public List<UserInfo> getUserListByRoleId(int roleId, int pageIndex, int pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        return userMapper.selectUsersByRoleId(roleId, offset, pageSize);
    }

    public void addUser(UserReq request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtils.generateBCryptPassword(request.getPassword()));
        user.setPhoto(request.getPhoto());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        userMapper.addUser(user);
        Long userId = user.getId();
        Authority authority = new Authority();
        authority.setRoleId(request.getRoleId());
        authority.setUserId(userId);
        authority.setUsername(user.getUsername());
        Role role = roleMapper.selectRoleById(request.getRoleId());
        authority.setAuthority(role.getRoleName());
        authorityMapper.addAuthority(authority);
        if(request.getRoleId() == 1){
            Organizer organizer = new Organizer();
            organizer.setUserId(userId);
            organizer.setOrganizationName(request.getUsername());
            organizer.setOrganizationDescription(request.getUsername());
            organizerMapper.addOrganizer(organizer);
        }
        if(request.getRoleId() == 2){
            Volunteer volunteer = new Volunteer();
            volunteer.setUserId(userId);
            volunteerMapper.addVolunteer(volunteer);
        }
    }

    public void updateUser(UserReq request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setUsername(request.getUsername());
        user.setPhoto(request.getPhoto());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setId(request.getId());
        userMapper.updateUser(user);
        Authority authority = new Authority();
        authority.setRoleId(request.getRoleName());
        authority.setUserId(request.getId());
        authority.setUsername(request.getUsername());
        Role role = roleMapper.selectRoleById(request.getRoleName());
        authority.setAuthority(role.getRoleName());
        authorityMapper.updateAuthority(authority);
    }

    public void deleteUser(UserReq request) {
        userMapper.deleteUser(request);
        authorityMapper.deleteAuthority(request);
    }

    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    public void updateUserProfile(Long loginId, String username, String phone, String email) {
        try {
            logger.debug("Attempting to update user profile for loginId: {}", loginId);
            User user = userMapper.findById(loginId);
            if (user == null) {
                logger.error("User not found with loginId: {}", loginId);
                throw new ResourceNotFoundException("User not found with id: " + loginId);
            }

            user.setUsername(username);
            user.setPhone(phone);
            user.setEmail(email);
            userMapper.updateUserProfile(user);
            logger.debug("User profile updated successfully for loginId: {}", loginId);
        } catch (Exception e) {
            logger.error("Error updating user profile", e);
            throw e;
        }
    }

    public List<VolunteerRating> getRatingsByVolunteerId(Long volunteerId) {
        return userMapper.findRatingsByVolunteerId(volunteerId);
    }



    public List<VolunteerInfo> getVolunteerInfoByVolunteerId(Long volunteerId) {
        return userMapper.findVolunteerInfoByVolunteerId(volunteerId);
    }

    public void addVolunteerInfo(VolunteerInfo volunteerInfo) {
        userMapper.addVolunteerInfo(volunteerInfo);
    }

    public void updateLoginTime(JwtRequest authenticationRequest) {
        userMapper.updateLoginTime(authenticationRequest.getUsername());
    }

    public User getUserByEmail(String to) {
        return userMapper.getUserByEmail(to);
    }

    public void updateUserAvator(Long volunteerId, String s) {
        userMapper.updateUserAvator(volunteerId,s);
    }

    public User getUserByEventRegistrationId(Long id) {
        return userMapper.getUserByEventRegistrationId(id);
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean changePassword(Long loginId, String currentPassword, String newPassword) {
        User user = userMapper.findById(loginId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateUser(user);
        return true;
    }

}