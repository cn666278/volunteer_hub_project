package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService; // Service for managing role-related operations

    /**
     * Endpoint to retrieve all roles.
     * @return ResultVO containing a list of all roles or a failure message if none are found.
     */
    @GetMapping("/admin/role/getRoleList")
    public ResultVO<List<Role>> getRoleList() {
        List<Role> roleInfos = roleService.getAllRoles(); // Fetch all roles from the service
        if (roleInfos != null) {
            return ResultVO.success(roleInfos); // Return the list of roles if found
        } else {
            return ResultVO.failure("getRoleList : not found!"); // Return failure message if no roles are found
        }
    }

    /**
     * Endpoint to add a new role.
     * @param role The role object to be added.
     * @return ResultVO containing a success message if the role is added successfully.
     */
    @PostMapping("/admin/role/addRole")
    public ResultVO<String> addRole(@RequestBody Role role) {
        roleService.addRole(role); // Add the new role using the service
        return ResultVO.success("Role added successfully");
    }

    /**
     * Endpoint to update an existing role.
     * @param role The role object with updated information.
     * @return ResultVO containing a success message if the role is updated successfully.
     */
    @PostMapping("/admin/role/updateRole")
    public ResultVO<String> updateRole(@RequestBody Role role) {
        roleService.updateRole(role); // Update the role using the service
        return ResultVO.success("Role updated successfully");
    }

    /**
     * Endpoint to delete a role.
     * @param role The role object to be deleted.
     * @return ResultVO containing a success message if the role is deleted successfully.
     */
    @PostMapping("/admin/role/deleteRole")
    public ResultVO<String> deleteRole(@RequestBody Role role) {
        roleService.deleteRole(role); // Delete the role using the service
        return ResultVO.success("Role deleted successfully");
    }
}
