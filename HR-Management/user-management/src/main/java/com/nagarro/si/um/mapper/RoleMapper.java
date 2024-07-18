package com.nagarro.si.um.mapper;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.roleName());
        return role;
    }

    public RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getRoleName());
    }
}
