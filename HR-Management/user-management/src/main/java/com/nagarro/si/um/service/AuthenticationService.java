package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.LogoutResponseDTO;
import com.nagarro.si.um.dto.AuthenticationResponseDTO;
import com.nagarro.si.um.dto.LoginDTO;
import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final RoleRepository roleRepository;

    public AuthenticationResponseDTO login(LoginDTO loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.email());
        String token = jwtService.generateToken(userDetails);

        return new AuthenticationResponseDTO(token);
    }

    public LogoutResponseDTO logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.addToBlacklist(token);
        } else {
            throw new IllegalArgumentException("Authorization header is missing or is invalid");
        }
        return new LogoutResponseDTO("User logged out successfully");
    }

    public UserDTO registerAdmin(UserDTO userDTO) {
        Role adminRole = roleRepository.findByRoleName("Admin")
                .orElseThrow(() -> new EntityNotFoundException("Admin role not found"));

        if (!adminRole.getRoleId().equals(userDTO.roleId())) {
            throw new IllegalArgumentException("The user must be an admin for registration");
        }

        UserDTO savedUserDTO = new UserDTO(
                userDTO.username(),
                userDTO.email(),
                userDTO.password(),
                userDTO.roleId()
        );

        return userService.createUser(savedUserDTO);
    }
}
