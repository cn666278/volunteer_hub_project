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
    private UserService userService;

    @Autowired
    private VolunteerService volunteerService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Get login user information based on username.
     * @param request contains the username.
     * @return user information if found.
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
     * Get the list of all users.
     * @return a list of all users.
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
     * Get user information by user ID.
     * @param request contains the user ID.
     * @return user information if found.
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
     * Add a new user.
     * @param request contains the user details.
     * @return success message if the user is added successfully.
     */
    @PostMapping("/admin/user/addUser")
    public ResultVO<String> addUser(@RequestBody UserReq request) {
        userService.addUser(request);
        return ResultVO.success("add success");
    }

    /**
     * Update an existing user.
     * @param request contains the updated user details.
     * @return success message if the user is updated successfully.
     */
    @PostMapping("/admin/user/updateUser")
    public ResultVO<String> updateUser(@RequestBody UserReq request) {
        userService.updateUser(request);
        return ResultVO.success("update success");
    }

    /**
     * Delete a user.
     * @param request contains the user ID to be deleted.
     * @return success message if the user is deleted successfully.
     */
    @PostMapping("/admin/user/deleteUser")
    public ResultVO<String> deleteUser(@RequestBody UserReq request) {
        userService.deleteUser(request);
        return ResultVO.success("delete success");
    }

    /**
     * Get user details by ID.
     * @param id the user ID.
     * @return the user details.
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Update the user profile.
     * @param user contains the updated user profile details.
     * @return success message if the profile is updated successfully.
     */
    @PutMapping("/user/updateProfile")
    public ResultVO<String> updateUserProfile(@RequestBody User user) {
        // Extract loginId, username, phone, and email from the User object
        Long loginId = user.getLoginId();
        String username = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();

        // Call the service layer to update the user information
        userService.updateUserProfile(loginId, username, phone, email);

        return ResultVO.success("User profile updated successfully");
    }

    /**
     * Get ratings by volunteer ID.
     * @param volunteerId the volunteer ID.
     * @return a list of ratings for the volunteer.
     */
    @GetMapping("/user/{volunteerId}/ratings")
    public ResultVO<List<VolunteerRating>> getRatingsByVolunteerId(@PathVariable Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId);
        List<VolunteerRating> ratings = userService.getRatingsByVolunteerId(volunteer.getId());
        if (ratings != null && !ratings.isEmpty()) {
            return ResultVO.success(ratings);
        } else {
            return ResultVO.success(Collections.emptyList()); // Return an empty list instead of a 500 error
        }
    }

    /**
     * Get volunteer information by volunteer ID.
     * @param volunteerId the volunteer ID.
     * @return a list of volunteer information.
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
     * Change the user password.
     * @param request contains the details for changing the password.
     * @return success message if the password is changed successfully.
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
            logger.error("Error changing password", e);
            return ResultVO.failure("Failed to change password");
        }
    }
}
