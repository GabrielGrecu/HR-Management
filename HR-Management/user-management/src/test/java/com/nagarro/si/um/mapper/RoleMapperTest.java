package com.nagarro.si.um.mapper;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleMapperTest {

    private final RoleMapper roleMapper = new RoleMapper();

    @Test
    void testToRole() {
        RoleDTO roleDTO = new RoleDTO("user");
        Role role = roleMapper.toRole(roleDTO);

        assertEquals("user", role.getRoleName());
    }

    @Test
    void testToRoleDTO() {
        Role role = new Role();
        role.setRoleName("user");

        RoleDTO roleDTO = roleMapper.toRoleDTO(role);

        assertEquals("user", roleDTO.roleName());
    }
}
