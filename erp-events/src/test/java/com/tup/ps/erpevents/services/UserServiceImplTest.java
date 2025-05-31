package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserUpdateDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserServiceImpl userService;

    private UserEntity user;
    private UserDTO userDTO;
    private UserUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setIdUser(1L);
        user.setEmail("test@example.com");
        user.setSoftDelete(false);

        userDTO = new UserDTO();
        userDTO.setIdUser(1L);

        updateDTO = new UserUpdateDTO();
        updateDTO.setFirstName("Updated");
        updateDTO.setPassword("newPassword");
    }

    @Test
    @DisplayName("US-001/Should return paged users")
    void testGetUsers() {
        Page<UserEntity> page = new PageImpl<>(List.of(user));
        given(userRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).willReturn(userDTO);

        Page<UserDTO> result = userService.getUsers(Pageable.unpaged());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("US-002/Should return user by ID")
    void testGetUserById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("US-003/Should soft delete user")
    void testSoftDeleteUser() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.softDeleteUser(1L, "test@example.com"));
        verify(userRepository).save(user);
        assertTrue(user.getSoftDelete());
    }

    @Test
    @DisplayName("US-004/Should update user")
    void testUpdateUser() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(userRepository.save(any())).willReturn(user);
        given(passwordEncoder.encode("newPassword")).willReturn("encodedPassword");
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        UserDTO result = userService.update(1L, "test@example.com", updateDTO);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("US-005/Should throw on invalid user update")
    void testUpdateUserThrowsOnInvalidId() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));

        assertThrows(ApiException.class, () -> userService.update(2L, "test@example.com", updateDTO));
    }

    @Test
    @DisplayName("US-006/Should upgrade user role")
    void testUpgradeUser() {
        RoleEntity role = new RoleEntity();
        role.setIdRole(2L);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(roleRepository.findByRoleCode(2)).willReturn(Optional.of(role));
        given(userRepository.save(user)).willReturn(user);
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        UserDTO result = userService.upgradeUser(1L, 2);
        assertNotNull(result);
    }

    @Test
    @DisplayName("US-007/Should return user details")
    void testUserDetails() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        UserDTO result = userService.userDetails(1L, "test@example.com");
        assertNotNull(result);
    }

    @Test
    @DisplayName("US-008/Should throw on mismatched user details")
    void testUserDetailsMismatch() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));

        assertThrows(ApiException.class, () -> userService.userDetails(2L, "test@example.com"));
    }

    @Test
    @DisplayName("US-009/Should find users by IDs")
    void testFindUsersByIds() {
        List<Long> ids = List.of(1L);
        given(userRepository.findAllById(ids)).willReturn(List.of(user));
        given(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).willReturn(userDTO);

        List<UserDTO> result = userService.findUsersByIds(ids);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("US-010/Should throw if no users found for IDs")
    void testFindUsersByIdsThrows() {
        List<Long> ids = List.of(99L);
        given(userRepository.findAllById(ids)).willReturn(List.of());

        assertThrows(ApiException.class, () -> userService.findUsersByIds(ids));
    }

    @Test
    @DisplayName("US-011/Should return user by email")
    void testLoadUserByUsername() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("test@example.com");
        assertNotNull(result);
    }

    @Test
    @DisplayName("US-012/Should throw if user by email not found")
    void testLoadUserByUsernameThrows() {
        given(userRepository.findByEmail("missing@example.com")).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.loadUserByUsername("missing@example.com"));
    }
}

