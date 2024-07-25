package com.nagarro.si.um.mapper;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    private UserDTO userDTO;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleId(1L);

        user = new User();
        user.setUsername("Gabriel Grecu");
        user.setEmail("grecualexandrugabriel@yahoo.com");
        user.setPassword("password");
        user.setRole(role);

        userDTO = new UserDTO(
                "Gabriel Grecu",
                "grecualexandrugabriel@yahoo.com",
                "password",
                1L
        );
    }

    @Test
    void testToUserDTO() {
        UserDTO actualUserDTO = userMapper.toUserDTO(user);

        assertEquals(userDTO, actualUserDTO);
    }

    @Test
    void testToUser() {
        User actualUser = userMapper.toUser(userDTO, role);

        assertEquals(user, actualUser);
    }
}