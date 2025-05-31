package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserUpdateDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.services.UserService;
import com.tup.ps.erpevents.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    private UserDTO user;
    private UserUpdateDTO userUpdate;

    @BeforeEach
    void setUp() {
        user = new UserDTO();
        user.setIdUser(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setDocumentType(DocumentType.DNI);
        user.setDocumentNumber("12345678");
        user.setSoftDelete(false);

        userUpdate = new UserUpdateDTO();
        userUpdate.setFirstName("John Updated");
        userUpdate.setLastName("Doe");
        userUpdate.setBirthDate(LocalDate.of(1990, 1, 1));
        userUpdate.setDocumentNumber("12345678");
        userUpdate.setPassword("newpassword");
    }

    @Test
    @DisplayName("UC-001/Should return all users successfully")
    void testGetAllUsersSuccess() throws Exception {
        Page<UserDTO> page = new PageImpl<>(List.of(user));
        given(userService.getUsers(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }

    @Test
    @DisplayName("UC-002/Should return user by ID successfully")
    void testGetUserByIdSuccess() throws Exception {
        given(userService.getUserById(1L)).willReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("UC-003/Should delete user successfully")
    void testDeleteUserSuccess() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("UC-004/Should update user successfully")
    void testUpdateUserSuccess() throws Exception {
        given(userService.update(eq(1L), anyString(), any(UserUpdateDTO.class))).willReturn(user);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("UC-005/Should get user details successfully")
    void testGetUserDetailsSuccess() throws Exception {
        given(userService.userDetails(eq(1L), anyString())).willReturn(user);

        mockMvc.perform(get("/users/1/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("UC-006/Should find users by IDs successfully")
    void testFindUsersByIdsSuccess() throws Exception {
        given(userService.findUsersByIds(List.of(1L))).willReturn(List.of(user));

        mockMvc.perform(post("/users/by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    @DisplayName("UC-007/Should upgrade user role successfully")
    void testUpgradeUserRoleSuccess() throws Exception {
        given(userService.upgradeUser(eq(1L), eq(2))).willReturn(user);

        mockMvc.perform(put("/users/1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
}

