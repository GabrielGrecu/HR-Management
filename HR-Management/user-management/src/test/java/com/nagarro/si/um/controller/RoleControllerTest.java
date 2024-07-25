package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO("user");
    }

    @Test
    void testCreateRole() {
        when(roleService.createRole(any(RoleDTO.class))).thenReturn(roleDTO);

        RoleDTO result = roleController.createRole(roleDTO);

        assertEquals(roleDTO, result);
    }

    @Test
    void testGetRoleById() {
        when(roleService.getRoleById(1L)).thenReturn(roleDTO);

        RoleDTO result = roleController.getRoleById(1L);

        assertEquals(roleDTO, result);
    }
}
