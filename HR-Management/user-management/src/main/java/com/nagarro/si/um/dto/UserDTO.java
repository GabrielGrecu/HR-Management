package com.nagarro.si.um.dto;

public record UserDTO(
        String username,
        String email,
        String password,
        Long roleId) {
}


