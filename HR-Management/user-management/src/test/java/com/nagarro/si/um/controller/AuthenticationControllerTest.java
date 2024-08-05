package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.AuthenticationResponseDTO;
import com.nagarro.si.um.dto.LoginDTO;
import com.nagarro.si.um.dto.LogoutResponseDTO;
import com.nagarro.si.um.dto.UserDTO;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void testLogin() {
        LoginDTO loginDto = new LoginDTO("username", "password");
        AuthenticationResponseDTO responseDto = new AuthenticationResponseDTO("token");

        when(authenticationService.login(loginDto)).thenReturn(responseDto);

        AuthenticationResponseDTO result = authenticationController.login(loginDto);

        assertEquals(responseDto, result);
        verify(authenticationService).login(loginDto);
    }

    @Test
    public void testLogout() {
        LogoutResponseDTO logoutResponseDTO = new LogoutResponseDTO("User logged out successfully");
        when(authenticationService.logout(request)).thenReturn(logoutResponseDTO);

        LogoutResponseDTO result = authenticationController.logout(request);

        assertEquals(logoutResponseDTO.logoutMessage(), result.logoutMessage());
    }

    @Test
    public void testRegisterAdmin() {
        UserDTO userDTO = new UserDTO("admin", "admin@gmail.com", "password", 1L);
        Role adminRole = new Role();
        adminRole.setRoleId(1L);
        adminRole.setRoleName("Admin");

        when(authenticationService.registerAdmin(userDTO)).thenReturn(userDTO);

        UserDTO result = authenticationController.registerAdmin(userDTO);

        assertEquals(userDTO, result);
    }
}
