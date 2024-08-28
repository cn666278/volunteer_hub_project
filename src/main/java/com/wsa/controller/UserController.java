package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService; // Service for managing user-related operations

    @Autowired
    private VolunteerService volunteerService; // Service for managing volunteer-related operations

    private static final Logger logger = LogManager.getLogger(UserController.class); // Logger for error handling

    /**
     * Endpoint to get the login user information by username.
     * @param request The user request containing the username.
     * @return ResultVO containing user information if found or a failure message.
     */
    @PostMapping("/getLoginUserInfo")
    public ResultVO<UserInfo> getLoginUserInfo(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoByUsername(request.getUsername());
        if (userInfo != null) {
            return ResultVO.success(userInfo);
        } else {
            return ResultVO.failure("getLoginUserInfo : not found!");
        }
    }

    /**
     * Endpoint to get a list of all users.
     * @return ResultVO containing a list of users or a failure message if none are found.
     */
    @GetMapping("/admin/user/getUserList")
    public ResultVO<UserListRes> getUserList() {
        List<UserInfo> userInfos = userService.getAllUsers();
        if (userInfos != null) {
            UserListRes userListRes = new UserListRes();
            userListRes.setCount(Long.valueOf(userInfos.size()));
            userListRes.setList(userInfos);
            return ResultVO.success(userListRes);
        } else {
            return ResultVO.failure("getUserList : not found!");
        }
    }

    /**
     * Endpoint to get user information by user ID.
     * @param request The user request containing the user ID.
     * @return ResultVO containing user information if found or a failure message.
     */
    @PostMapping("/admin/user/getUserById")
    public ResultVO<UserInfo> getUserById(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoById(request.getId());
        if (userInfo != null) {
            return ResultVO.success(userInfo);
        } else {
            return ResultVO.failure("getUserById : not found!");
        }
    }

    /**
     * Endpoint to add a new user.
     * @param request The user request containing user details.
     * @return ResultVO containing a success message if the user is added successfully.
     */
    @PostMapping("/admin/user/addUser")
    public ResultVO<String> addUser(@RequestBody UserReq request) {
        userService.addUser(request);
        return ResultVO.success("User added successfully");
    }

    /**
     * Endpoint to update user information.
     * @param request The user request containing updated user details.
     * @return ResultVO containing a success message if the user is updated successfully.
     */
    @PostMapping("/admin/user/updateUser")
    public ResultVO<String> updateUser(@RequestBody UserReq request) {
        userService.updateUser(request);
        return ResultVO.success("User updated successfully");
    }

    /**
     * Endpoint to delete a user.
     * @param request The user request containing user details to delete.
     * @return ResultVO containing a success message if the user is deleted successfully.
     */
    @PostMapping("/admin/user/deleteUser")
    public ResultVO<String> deleteUser(@RequestBody UserReq request) {
        userService.deleteUser(request);
        return ResultVO.success("User deleted successfully");
    }

    /**
     * Endpoint to get user information by user ID.
     * @param id The ID of the user.
     * @return The user object containing user details.
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Endpoint to update user profile information.
     * @param user The user object containing updated profile information.
     * @return ResultVO containing a success message if the profile is updated successfully.
     */
    @PutMapping("/user/updateProfile")
    public ResultVO<String> updateUserProfile(@RequestBody User user) {
        // Retrieve loginId, username, phone, and email from the User object
        Long loginId = user.getLoginId();
        String username = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();

        // Call service layer method to update user profile
        userService.updateUserProfile(loginId, username, phone, email);

        return ResultVO.success("User profile updated successfully");
    }

    /**
     * Endpoint to get ratings by volunteer ID.
     * @param volunteerId The ID of the volunteer.
     * @return ResultVO containing a list of volunteer ratings or an empty list if none found.
     */
    @GetMapping("/user/{volunteerId}/ratings")
    public ResultVO<List<VolunteerRating>> getRatingsByVolunteerId(@PathVariable Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId);
        List<VolunteerRating> ratings = userService.getRatingsByVolunteerId(volunteer.getId());
        if (ratings != null && !ratings.isEmpty()) {
            return ResultVO.success(ratings);
        } else {
            return ResultVO.success(Collections.emptyList()); // Return an empty list instead of 500 error
        }
    }

    /**
     * Endpoint to get volunteer information by volunteer ID.
     * @param volunteerId The ID of the volunteer.
     * @return ResultVO containing a list of volunteer information or an empty list if none found.
     */
    @GetMapping("/user/{volunteerId}/volunteerinfo")
    public ResultVO<List<VolunteerInfo>> getVolunteerInfo(@PathVariable Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId);
        List<VolunteerInfo> volunteerInfoList = userService.getVolunteerInfoByVolunteerId(volunteer.getId());
        if (volunteerInfoList != null && !volunteerInfoList.isEmpty()) {
            return ResultVO.success(volunteerInfoList);
        } else {
            return ResultVO.success(Collections.emptyList()); // Return an empty list instead of a 500 error
        }
    }

    /**
     * Endpoint to change the user's password.
     * @param request The change password request containing login ID, current password, and new password.
     * @return ResultVO containing a success message if the password is changed successfully, otherwise a failure message.
     */
    @PostMapping("/user/changePassword")
    public ResultVO<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            boolean isChanged = userService.changePassword(request.getLoginId(), request.getCurrentPassword(), request.getNewPassword());
            if (isChanged) {
                return ResultVO.success("Password changed successfully");
            } else {
                return ResultVO.failure("Current password is incorrect");
            }
        } catch (Exception e) {
            logger.error("Error changing password", e); // Log error details
            return ResultVO.failure("Failed to change password");
        }
    }
}
