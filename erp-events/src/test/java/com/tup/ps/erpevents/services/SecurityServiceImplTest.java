package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.notification.template.TemplateDTO;
import com.tup.ps.erpevents.dtos.role.RoleDTO;
import com.tup.ps.erpevents.dtos.user.*;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.RoleName;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.services.impl.SecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceImplTest {

    @Mock private ModelMapper modelMapper;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JWTService jwtService;
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private TemplateService templateService;
    @Mock private NotificationService notificationService;

    @InjectMocks private SecurityServiceImpl securityService;

    private UserEntity user;
    private UserRegisterDTO registerDTO;
    private RoleEntity role;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setIdUser(1L);
        user.setEmail("test@example.com");
        user.setFirstName("Test");

        registerDTO = new UserRegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");

        role = new RoleEntity();
        role.setIdRole(1L);
    }

    @Test
    @DisplayName("SE-001/Should authenticate and return JWT on login")
    void testLogin() {
        UserLoginDTO loginDTO = new UserLoginDTO("test@example.com", "password");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication auth = mock(Authentication.class);
        given(auth.getPrincipal()).willReturn(user);
        given(authenticationManager.authenticate(authToken)).willReturn(auth);
        given(jwtService.createToken("test@example.com", 60)).willReturn("fake-jwt-token");

        String token = securityService.login(loginDTO);

        assertEquals("fake-jwt-token", token);
    }

    @Test
    @DisplayName("SE-002/Should search and return user DTO")
    void testSearchUser() {
        UserLoginDTO loginDTO = new UserLoginDTO("test@example.com", "password");
        user.setRole(role);

        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(modelMapper.map(user, UserDTO.class)).willReturn(new UserDTO());
        given(modelMapper.map(role, RoleDTO.class)).willReturn(new RoleDTO());

        UserDTO result = securityService.searchUser(loginDTO);
        assertNotNull(result);
    }

    @Test
    @DisplayName("SE-003/Should register a new user")
    void testRegisterUser() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.empty());
        given(roleRepository.findByName(RoleName.EMPLOYEE)).willReturn(Optional.of(role));
        given(passwordEncoder.encode("password")).willReturn("hashed");
        given(modelMapper.map(any(UserEntity.class), eq(UserDTO.class))).willReturn(new UserDTO());

        UserDTO result = securityService.registerUser(registerDTO);
        assertNotNull(result);
    }

    @Test
    @DisplayName("SE-004/Should encrypt password")
    void testEncryptPassword() {
        given(passwordEncoder.encode("123456")).willReturn("encoded");

        String encoded = securityService.encryptPassword("123456");

        assertEquals("encoded", encoded);
    }

    @Test
    @DisplayName("SE-005/Should throw if user already exists on register")
    void testRegisterUserAlreadyExists() {
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));

        assertThrows(ApiException.class, () -> securityService.registerUser(registerDTO));
    }

    @Test
    @DisplayName("SE-006/Should change user password")
    void testChangePassword() {
        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setNewPassword("newPass");
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.encode("newPass")).willReturn("hashedPass");

        assertDoesNotThrow(() -> securityService.changePassword("test@example.com", dto));
    }

    @Test
    @DisplayName("SE-007/Should reset password with valid token")
    void testResetPassword() {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setToken("token");
        dto.setNewPassword("resetPass");

        given(jwtService.extractUserId("token")).willReturn("test@example.com");
        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.encode("resetPass")).willReturn("hashedPass");

        assertDoesNotThrow(() -> securityService.resetPassword(dto));
    }

    @Test
    @DisplayName("SE-008/Should send recovery email")
    void testSendRecoveryEmail() {
        TemplateDTO template = new TemplateDTO();
        template.setIdTemplate(3L);

        given(userRepository.findByEmail("test@example.com")).willReturn(Optional.of(user));
        given(jwtService.createToken("test@example.com", 15)).willReturn("token");
        given(templateService.getEmailTemplateById(3L)).willReturn(template);

        assertDoesNotThrow(() -> securityService.sendRecoveryEmail("test@example.com"));
    }
}

