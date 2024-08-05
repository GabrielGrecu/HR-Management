package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.repository.RoleRepository;
import com.nagarro.si.um.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(
                "Gabriel Grecu",
                "grecualexandrugabriel@yahoo.com",
                "password",
                1L
        );

        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("user");

        user = new User();
        user.setUserId(1L);
        user.setUsername("Gabriel Grecu");
        user.setEmail("grecualexandrugabriel@yahoo.com");
        user.setPassword("password");
        user.setRole(role);
    }

    @Test
    void testCreateUser() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userMapper.toUser(any(UserDTO.class), any(Role.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(userDTO, result);
        verify(roleRepository).findById(1L);
    }

    @Test
    void testCreateUserWhenEntityNotFoundException() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.createUser(userDTO));

        String expectedMessage = "Role with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roleRepository).findById(1L);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertEquals(userDTO, result);
        verify(userRepository).findById(1L);
        verify(userMapper).toUserDTO(any(User.class));
    }

    @Test
    void testGetUserByIdWhenEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(1L));

        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository).findById(1L);
        verify(userMapper, never()).toUserDTO(any(User.class));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.updateUser(1L, userDTO);

        assertEquals(userDTO, result);
        verify(userRepository).findById(1L);
        verify(roleRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserWhenUserNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L, userDTO));

        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository).findById(1L);
        verify(roleRepository, never()).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserWhenRoleNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L, userDTO));

        String expectedMessage = "Role with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository).findById(1L);
        verify(roleRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository).findById(1L);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void testDeleteUserWhenEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(1L));

        String expectedMessage = "User with ID 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository).findById(1L);
    }
}