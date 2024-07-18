package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.RoleDTO;
import com.nagarro.si.um.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Long id) {
        RoleDTO roleDTO = roleService.getRoleById(id);
        return ResponseEntity.ok(roleDTO);
    }
}
