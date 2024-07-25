package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(
                "Gabriel Grecu",
                "grecualexandrugabriel@yahoo.com",
                "password",
                1L
        );
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userController.createUser(userDTO);

        assertEquals(userDTO, result);
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(userDTO);

        UserDTO result = userController.getUserById(1L);

        assertEquals(userDTO, result);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userController.updateUser(1L, userDTO);

        assertEquals(userDTO, result);
    }

    @Test
    void testDeleteUser() {
        assertDoesNotThrow(() -> userController.deleteUser(1L));

        verify(userService, times(1)).deleteUser(1L);
    }
}
