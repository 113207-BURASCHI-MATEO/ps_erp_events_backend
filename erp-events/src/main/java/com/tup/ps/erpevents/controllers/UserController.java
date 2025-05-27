package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserUpdateDTO;
import com.tup.ps.erpevents.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@AllArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @Operation(summary = "Obtener usuario por id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Baja logica de un usuario por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        userService.softDeleteUser(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizacion de un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.update(id, userDetails.getUsername(), userUpdateDTO));
    }

    @Operation(summary = "Detalles de un usuario")
    @GetMapping("/{id}/details")
    public ResponseEntity<UserDTO> getUserDetails(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.userDetails(id, userDetails.getUsername()));
    }

    @Operation(summary = "Obtener usuarios por una lista de ids")
    @PostMapping("/by-ids")
    public ResponseEntity<List<UserDTO>> findUsersByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(userService.findUsersByIds(ids));
    }

    @Operation(summary = "Actualizacion de un usuario")
    @PutMapping("/{idUser}/role")
    public ResponseEntity<UserDTO> upgradeUser(
            @PathVariable Long idUser,
            @RequestBody Integer roleCode) {
        return ResponseEntity.ok(userService.upgradeUser(idUser, roleCode));
    }
}
