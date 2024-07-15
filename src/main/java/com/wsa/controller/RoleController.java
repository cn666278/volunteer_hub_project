package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/role/getRoleList")
    public ResultVO<List<Role>> getRoleList() {
        List<Role> roleInfos = roleService.getAllRoles();
        if (roleInfos != null) {
            return ResultVO.success(roleInfos);
        } else {
            return ResultVO.failure("getRoleList : not found!");
        }
    }

    @PostMapping("/admin/role/addRole")
    public ResultVO<String> addRole(@RequestBody Role role) {
        roleService.addRole(role);
        return ResultVO.success("add success");
    }

    @PostMapping("/admin/role/updateRole")
    public ResultVO<String> updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResultVO.success("update success");
    }

    @PostMapping("/admin/role/deleteRole")
    public ResultVO<String> deleteRole(@RequestBody Role role) {
        roleService.deleteRole(role);
        return ResultVO.success("delete success");
    }
}