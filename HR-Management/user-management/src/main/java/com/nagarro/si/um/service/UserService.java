package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.repository.RoleRepository;
import com.nagarro.si.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(UserDTO userDTO) {
        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        User user = new User();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(role);
        return userRepository.save(user);
    }
}
