package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.UserNotFoundException;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return userMapper.toUserDTO(user);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return userMapper.toUserDTO(createdUser);
    }
}
