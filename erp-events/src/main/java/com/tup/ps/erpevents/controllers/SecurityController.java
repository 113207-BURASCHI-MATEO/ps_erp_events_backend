package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.constants.Constants;
import com.tup.ps.erpevents.dtos.user.*;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.services.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para la autenticación de usuarios")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Operation(summary = "Iniciar sesión de usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO, HttpServletResponse response){
        try {
            var token = securityService.login(userLoginDTO);
            addJwtToCookie(response, token);

            UserDTO userReturn = securityService.searchUser(userLoginDTO);
            return ResponseEntity.ok(userReturn);
        } catch (AuthenticationException | javax.naming.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña invalido: " +e.getMessage() );
        }
    }


    private void addJwtToCookie(HttpServletResponse response, String token) {
        var cookie = new Cookie(Constants.getJwtCookieName(), token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(Constants.getJwtExpirationMinutes() * 60);
        response.addCookie(cookie);
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Operation(summary = "Registrar nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO registerRequest, HttpServletResponse response) throws javax.naming.AuthenticationException {
        var newUser = securityService.registerUser(registerRequest);
        var userLoginDTO = new UserLoginDTO(registerRequest.getEmail(), registerRequest.getPassword());
        var token = securityService.login(userLoginDTO);
        addJwtToCookie(response, token);
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Registrar nuevo usuario administrador")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid UserRegisterDTO registerRequest) {
        var newAdmin = securityService.registerAdmin(registerRequest);
        return ResponseEntity.ok(newAdmin);
    }

    @Operation(summary = "Solicitar recuperación de contraseña")
    @PostMapping("/recover-password")
    public ResponseEntity<?> recoverPassword(@RequestParam @Email String email) {
        try {
            securityService.sendRecoveryEmail(email);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al enviar correo: " + e.getMessage());
        }
    }

    @Operation(summary = "Restablecer contraseña con token de recuperación")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDTO dto) {
        try {
            securityService.resetPassword(dto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al restablecer contraseña: " + e.getMessage());
        }
    }

    @Operation(summary = "Cambiar contraseña del usuario logueado")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeDTO dto,
                                            @AuthenticationPrincipal UserEntity user) {
        try {
            securityService.changePassword(user.getEmail(), dto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
