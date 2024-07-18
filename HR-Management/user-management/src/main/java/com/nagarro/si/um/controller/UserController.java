package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDto = userMapper.toUserDTO(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        UserDTO createdUserDTO = userMapper.toUserDTO(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }
}
