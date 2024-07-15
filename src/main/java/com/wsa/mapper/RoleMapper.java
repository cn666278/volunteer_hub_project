package com.wsa.mapper;

import com.wsa.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    Role selectRoleById(Long roleId);

    List<Role> getAllRoles();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Role role);
}
