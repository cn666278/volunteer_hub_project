package com.wsa.service;

import com.wsa.mapper.RoleMapper;
import com.wsa.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    public void updateRole(Role role) {
        roleMapper.updateRole(role);
    }

    public void deleteRole(Role role) {
        roleMapper.deleteRole(role);
    }
}
