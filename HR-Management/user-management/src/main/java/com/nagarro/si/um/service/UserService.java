package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.RoleNotFoundException;
import com.nagarro.si.um.exception.UserNotFoundException;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.repository.RoleRepository;
import com.nagarro.si.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }


    public User createUser(UserDTO userDTO) {
        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + userDTO.roleId() + " not found"));

        User user = userMapper.toUser(userDTO, role);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }
}
