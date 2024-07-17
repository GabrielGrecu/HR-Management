package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.mapper.UserMapper;
import com.nagarro.si.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
