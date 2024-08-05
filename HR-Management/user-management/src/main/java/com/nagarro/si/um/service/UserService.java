package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.EntityAlreadyExistsException;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.repository.RoleRepository;
import com.nagarro.si.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new EntityAlreadyExistsException("User with email " + userDTO.email() + " already exists");
        }

        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Role with ID " + userDTO.roleId() + " not found"));

        User user = userMapper.toUser(userDTO, role);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
        return userMapper.toUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Role with ID " + userDTO.roleId() + " not found"));

        userMapper.updateUserFromDTO(user, userDTO, role);

        if (Objects.nonNull(userDTO.password()) && !userDTO.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.password()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
        userRepository.delete(user);
    }
}
