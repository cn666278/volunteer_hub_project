package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/getLoginUserInfo")
    public ResultVO<UserInfo> getLoginUserInfo(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoByUsername(request.getUsername());
        if (userInfo != null) {
            return ResultVO.success(userInfo);
        } else {
            return ResultVO.failure("getLoginUserInfo : not found!");
        }
    }
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
    @PostMapping("/admin/user/getUserById")
    public ResultVO<UserInfo> getUserById(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoById(request.getId());
        if (userInfo != null) {
            return ResultVO.success(userInfo);
        } else {
            return ResultVO.failure("getUserById : not found!");
        }
    }

    @PostMapping("/admin/user/addUser")
    public ResultVO<String> addUser(@RequestBody UserReq request) {
        userService.addUser(request);
        return ResultVO.success("add success");
    }

    @PostMapping("/admin/user/updateUser")
    public ResultVO<String> updateUser(@RequestBody UserReq request) {
        userService.updateUser(request);
        return ResultVO.success("update success");
    }

    @PostMapping("/admin/user/deleteUser")
    public ResultVO<String> deleteUser(@RequestBody UserReq request) {
        userService.deleteUser(request);
        return ResultVO.success("delete success");
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/user/updateProfile")
    public ResultVO<String> updateUserProfile(@RequestBody User user) {
        // 从 User 对象中获取 loginId, username, phone 和 email
        Long loginId = user.getLoginId();
        String username = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();

        // 调用服务层的方法来更新用户信息
        userService.updateUserProfile(loginId, username, phone, email);

        return ResultVO.success("User profile updated successfully");
    }

    @GetMapping("/user/{volunteerId}/ratings")
    public ResultVO<List<VolunteerRating>> getRatingsByVolunteerId(@PathVariable Long volunteerId) {
        List<VolunteerRating> ratings = userService.getRatingsByVolunteerId(volunteerId);
        if (ratings != null && !ratings.isEmpty()) {
            return ResultVO.success(ratings);
        } else {
            return ResultVO.success(Collections.emptyList()); // 返回空数组，而不是500错误
        }
    }

    @GetMapping("/user/{volunteerId}/volunteerinfo")
    public ResultVO<List<VolunteerInfo>> getVolunteerInfo(@PathVariable Long volunteerId) {
        List<VolunteerInfo> volunteerInfoList = userService.getVolunteerInfoByVolunteerId(volunteerId);
        if (volunteerInfoList != null && !volunteerInfoList.isEmpty()) {
            return ResultVO.success(volunteerInfoList);
        } else {
            return ResultVO.success(Collections.emptyList()); // Return an empty list instead of a 500 error
        }
    }


}