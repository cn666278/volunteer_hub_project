package com.wsa.controller;

import com.wsa.model.User;
import com.wsa.model.UserInfo;
import com.wsa.model.UserListRequest;
import com.wsa.model.UserRequest;
import com.wsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/admin/getUserInfo")
    public ResponseEntity<UserInfo> getUserInfo(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoByUsername(request.getUsername());
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/getUserByLoginId")
    public ResponseEntity<UserInfo> getUserByLoginId(@RequestBody UserRequest request) {
        UserInfo userInfo = userService.getUserInfoByLoginId(request.getLoginId());
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/getUserList")
    public ResponseEntity<List<UserInfo>> getUserList(@RequestBody UserListRequest request) {
        List<UserInfo> userList = userService.getUserListByRoleId(request.getRoleId(), request.getPageIndex(), request.getPageSize());
        return ResponseEntity.ok(userList);
    }
}