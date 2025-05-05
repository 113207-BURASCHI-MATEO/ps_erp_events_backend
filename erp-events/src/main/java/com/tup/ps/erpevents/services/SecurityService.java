package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.dtos.user.UserLoginDTO;
import com.tup.ps.erpevents.dtos.user.UserRegisterDTO;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.enums.RoleName;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Interfaz para los servicios de autenticación y registro de usuarios.
 * Gestiona login, creación de usuarios (usuario y admin) y utilidades de seguridad.
 */
@Service
public interface SecurityService {

    /**
     * Autentica un usuario con email y contraseña.
     *
     * @param us DTO con credenciales del usuario.
     * @return Token JWT generado si las credenciales son válidas.
     * @throws AuthenticationException si las credenciales no son válidas.
     */
    String login(UserLoginDTO us) throws AuthenticationException;

    /**
     * Busca un usuario existente por su email y devuelve su información.
     *
     * @param us DTO con el email a buscar.
     * @return DTO del usuario si existe, o null si no se encuentra.
     */
    UserDTO searchUser(UserLoginDTO us);

    /**
     * Registra un nuevo usuario con rol USER por defecto.
     *
     * @param registerRequest Datos del nuevo usuario.
     * @return DTO del usuario creado.
     */
    UserDTO registerUser(UserRegisterDTO registerRequest);

    /**
     * Registra un nuevo administrador con rol ADMIN.
     *
     * @param registerRequest Datos del nuevo administrador.
     * @return DTO del usuario creado.
     */
    UserDTO registerAdmin(UserRegisterDTO registerRequest);

    /**
     * Registra un nuevo usuario con un rol específico.
     *
     * @param registerRequest Datos del nuevo usuario.
     * @param roleName Rol a asignar (USER, ADMIN, etc.).
     * @return DTO del usuario creado.
     */
    UserDTO register(UserRegisterDTO registerRequest, RoleName roleName);

    /**
     * Registra un nuevo usuario con un rol específico.
     *
     * @param registerRequest Datos del nuevo usuario.
     * @param roleName Rol a asignar (USER, ADMIN, etc.).
     * @return DTO del usuario creado.
     */
    UserEntity registerEntity(UserRegisterDTO registerRequest, RoleName roleName);

    /**
     * Codifica una contraseña en texto plano.
     *
     * @param password Contraseña sin encriptar.
     * @return Contraseña codificada.
     */
    String encryptPassword(String password);
}
