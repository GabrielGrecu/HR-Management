package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.AuthenticationResponseDTO;
import com.nagarro.si.um.dto.LoginDTO;
import com.nagarro.si.um.dto.LogoutResponseDTO;
import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginDTO loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/logout")
    public LogoutResponseDTO logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }

    @PostMapping("/register-admin")
    public UserDTO registerAdmin(@RequestBody UserDTO userDTO) {
        return authenticationService.registerAdmin(userDTO);
    }
}
