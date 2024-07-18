package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.exception.RoleNotFoundException;
import com.nagarro.si.um.mapper.RoleMapper;
import com.nagarro.si.um.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDTO(savedRole);
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found"));
        return roleMapper.toDTO(role);
    }
}
