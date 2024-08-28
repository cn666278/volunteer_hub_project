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

/**
 * Service class for managing user-related operations.
 * This service interacts with various data access layers to handle user data, roles, authorities,
 * organizers, volunteers, and their information.
 */
@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class); // Logger for debugging and error messages

    @Autowired
    private UserMapper userMapper; // Mapper for user-related database interactions

    @Autowired
    private AuthorityMapper authorityMapper; // Mapper for authority-related database interactions

    @Autowired
    private OrganizerMapper organizerMapper; // Mapper for organizer-related database interactions

    @Autowired
    private VolunteerMapper volunteerMapper; // Mapper for volunteer-related database interactions

    @Autowired
    private RoleMapper roleMapper; // Mapper for role-related database interactions

    /**
     * Retrieves all users and their roles.
     * Throws an exception if no users are found.
     * @return List of UserInfo objects representing all users and their details.
     */
    public List<UserInfo> getAllUsers() {
        List<UserInfo> userInfos = new ArrayList<>();
        List<User> users = userMapper.selectAllUsers();
        if (users != null) {
            for (User user : users) {
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

    /**
     * Finds authorities by user ID and sets them in the provided UserInfo object.
     * @param userInfo The UserInfo object to update with role details.
     */
    private void findAuthoritiesByUserId(UserInfo userInfo) {
        List<Authority> authoritiesByUserId = userMapper.findAuthoritiesByUserId(userInfo.getId());
        if (authoritiesByUserId != null) {
            Authority authority = authoritiesByUserId.get(0);
            UserInfo.Role role = new UserInfo.Role();
            role.setRoleName(authority.getAuthority());
            role.setRoleId(String.valueOf(authority.getRoleId()));
            userInfo.setRole(role);
        }
    }

    /**
     * Retrieves user information by username.
     * @param username The username of the user.
     * @return UserInfo object containing user details.
     */
    public UserInfo getUserInfoByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return getUserInfo(user);
    }

    /**
     * Retrieves user information by user ID.
     * @param id The ID of the user.
     * @return UserInfo object containing user details.
     */
    public UserInfo getUserInfoById(Long id) {
        User user = userMapper.findById(id);
        return getUserInfo(user);
    }

    /**
     * Helper method to populate UserInfo object from User entity.
     * @param user The User entity.
     * @return UserInfo object with populated data.
     */
    private UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        if (user != null) {
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

    /**
     * Retrieves a paginated list of users by role ID.
     * @param roleId The role ID to filter users by.
     * @param pageIndex The current page index.
     * @param pageSize The number of records per page.
     * @return List of UserInfo objects.
     */
    public List<UserInfo> getUserListByRoleId(int roleId, int pageIndex, int pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        return userMapper.selectUsersByRoleId(roleId, offset, pageSize);
    }

    /**
     * Adds a new user to the system.
     * Creates the user, assigns roles, and adds them as an organizer or volunteer based on their role.
     * @param request The user request object containing user details.
     */
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

        // Assign authority
        Authority authority = new Authority();
        authority.setRoleId(request.getRoleId());
        authority.setUserId(userId);
        authority.setUsername(user.getUsername());
        Role role = roleMapper.selectRoleById(request.getRoleId());
        authority.setAuthority(role.getRoleName());
        authorityMapper.addAuthority(authority);

        // Handle user roles as organizer or volunteer
        if (request.getRoleId() == 1) {
            Organizer organizer = new Organizer();
            organizer.setUserId(userId);
            organizer.setOrganizationName(request.getUsername());
            organizer.setOrganizationDescription(request.getUsername());
            organizerMapper.addOrganizer(organizer);
        }
        if (request.getRoleId() == 2) {
            Volunteer volunteer = new Volunteer();
            volunteer.setUserId(userId);
            volunteerMapper.addVolunteer(volunteer);
        }
    }

    /**
     * Updates user information.
     * @param request The user request containing updated details.
     */
    public void updateUser(UserReq request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setUsername(request.getUsername());
        user.setPhoto(request.getPhoto());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setId(request.getId());
        userMapper.updateUser(user);

        // Update authority
        Authority authority = new Authority();
        authority.setRoleId(request.getRoleName());
        authority.setUserId(request.getId());
        authority.setUsername(request.getUsername());
        Role role = roleMapper.selectRoleById(request.getRoleName());
        authority.setAuthority(role.getRoleName());
        authorityMapper.updateAuthority(authority);
    }

    /**
     * Deletes a user and their associated authority.
     * @param request The user request containing user details to delete.
     */
    public void deleteUser(UserReq request) {
        userMapper.deleteUser(request);
        authorityMapper.deleteAuthority(request);
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user.
     * @return The User object.
     */
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    /**
     * Updates the user's profile information.
     * @param loginId The login ID of the user.
     * @param username The updated username.
     * @param phone The updated phone number.
     * @param email The updated email address.
     */
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

    /**
     * Retrieves volunteer ratings by volunteer ID.
     * @param volunteerId The ID of the volunteer.
     * @return List of VolunteerRating objects.
     */
    public List<VolunteerRating> getRatingsByVolunteerId(Long volunteerId) {
        return userMapper.findRatingsByVolunteerId(volunteerId);
    }

    /**
     * Retrieves volunteer information by volunteer ID.
     * @param volunteerId The ID of the volunteer.
     * @return List of VolunteerInfo objects.
     */
    public List<VolunteerInfo> getVolunteerInfoByVolunteerId(Long volunteerId) {
        return userMapper.findVolunteerInfoByVolunteerId(volunteerId);
    }

    /**
     * Adds volunteer information.
     * @param volunteerInfo The volunteer information to be added.
     */
    public void addVolunteerInfo(VolunteerInfo volunteerInfo) {
        userMapper.addVolunteerInfo(volunteerInfo);
    }

    /**
     * Updates the last login time of the user.
     * @param authenticationRequest The authentication request containing the username.
     */
    public void updateLoginTime(JwtRequest authenticationRequest) {
        userMapper.updateLoginTime(authenticationRequest.getUsername());
    }

    /**
     * Retrieves a user by their email.
     * @param to The email address of the user.
     * @return The User object.
     */
    public User getUserByEmail(String to) {
        return userMapper.getUserByEmail(to);
    }

    /**
     * Updates the user's avatar.
     * @param volunteerId The ID of the volunteer.
     * @param avatar The new avatar URL or path.
     */
    public void updateUserAvator(Long volunteerId, String avatar) {
        userMapper.updateUserAvator(volunteerId, avatar);
    }

    /**
     * Retrieves a user by event registration ID.
     * @param id The ID of the event registration.
     * @return The User object.
     */
    public User getUserByEventRegistrationId(Long id) {
        return userMapper.getUserByEventRegistrationId(id);
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password encoder for handling password changes

    /**
     * Changes the user's password.
     * @param loginId The login ID of the user.
     * @param currentPassword The current password of the user.
     * @param newPassword The new password to be set.
     * @return True if the password was successfully changed.
     */
    public boolean changePassword(Long loginId, String currentPassword, String newPassword) {
        User user = userMapper.findById(loginId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateUser(user);
        return true;
    }
}
