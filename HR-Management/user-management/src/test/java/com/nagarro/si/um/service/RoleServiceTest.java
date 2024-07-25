package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.RoleMapper;
import com.nagarro.si.um.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    private RoleDTO roleDTO;
    private Role role;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO("user");

        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("user");
    }

    @Test
    void testCreateRole() {
        when(roleMapper.toRole(any(RoleDTO.class))).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.toRoleDTO(any(Role.class))).thenReturn(roleDTO);

        RoleDTO result = roleService.createRole(roleDTO);

        assertEquals(roleDTO, result);
        verify(roleMapper).toRole(any(RoleDTO.class));
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toRoleDTO(any(Role.class))).thenReturn(new RoleDTO("user"));

        RoleDTO result = roleService.getRoleById(1L);

        assertEquals(new RoleDTO("user"), result);
        verify(roleRepository).findById(1L);
        verify(roleMapper).toRoleDTO(any(Role.class));
    }

    @Test
    void testGetRoleByIdWhenEntityNotFoundException() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> roleService.getRoleById(1L));

        String expectedMessage = "Role with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roleRepository).findById(1L);
    }
}
