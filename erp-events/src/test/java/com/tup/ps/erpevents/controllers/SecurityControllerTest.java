package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.user.*;
import com.tup.ps.erpevents.entities.RoleEntity;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.RoleName;
import com.tup.ps.erpevents.services.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SecurityService securityService;

    private UserLoginDTO userLoginDTO;
    private UserRegisterDTO userRegisterDTO;
    private PasswordResetDTO passwordResetDTO;
    private PasswordChangeDTO passwordChangeDTO;
    private UserDTO userDTO;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        userLoginDTO = new UserLoginDTO("test@mail.com", "Password123!");

        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("test@mail.com");
        userRegisterDTO.setPassword("Password@123");
        userRegisterDTO.setFirstName("Test");
        userRegisterDTO.setLastName("User");
        userRegisterDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        userRegisterDTO.setDocumentType(DocumentType.DNI);
        userRegisterDTO.setDocumentNumber("12345678");

        passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setToken("reset-token");
        passwordResetDTO.setNewPassword("ResetPass@123");

        passwordChangeDTO = new PasswordChangeDTO();
        passwordChangeDTO.setNewPassword("NewPass@123");

        userDTO = new UserDTO();
        userDTO.setEmail("test@mail.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");

        RoleEntity role = new RoleEntity();
        role.setName(RoleName.ADMIN);
        user = new UserEntity();
        user.setIdUser(1L);
        user.setEmail("user@mail.com");
        user.setPassword("hashedPassword");
        user.setRole(role);
    }

    @Test
    @DisplayName("SC-001/Should login user successfully")
    void testLoginSuccess() throws Exception {
        given(securityService.login(any(UserLoginDTO.class))).willReturn("mock-token");
        given(securityService.searchUser(any(UserLoginDTO.class))).willReturn(userDTO);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    @Test
    @DisplayName("SC-002/Should register user successfully")
    void testRegisterUserSuccess() throws Exception {
        given(securityService.registerUser(any())).willReturn(userDTO);
        given(securityService.login(any())).willReturn("mock-token");
        given(securityService.searchUser(any())).willReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    @Test
    @DisplayName("SC-003/Should register admin successfully")
    void testRegisterAdminSuccess() throws Exception {
        given(securityService.registerAdmin(any())).willReturn(userDTO);

        mockMvc.perform(post("/auth/register-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    @Test
    @DisplayName("SC-004/Should recover password successfully")
    void testRecoverPasswordSuccess() throws Exception {
        mockMvc.perform(post("/auth/recover-password")
                        .param("email", "test@mail.com"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("SC-005/Should reset password successfully")
    void testResetPasswordSuccess() throws Exception {
        mockMvc.perform(post("/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordResetDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("SC-006/Should change password successfully")
    void testChangePasswordSuccess() throws Exception {
        mockMvc.perform(put("/auth/change-password")
                        .with(user(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChangeDTO)))
                .andExpect(status().isNoContent());
    }
}