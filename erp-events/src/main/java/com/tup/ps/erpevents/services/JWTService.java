package com.tup.ps.erpevents.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * Interfaz para la gestión de tokens JWT dentro del sistema.
 * Permite crear, verificar y extraer información de tokens,
 * así como obtener el token desde cookies o cabecera.
 */
@Service
public interface JWTService {

    /**
     * Genera un token JWT válido por cierta cantidad de minutos.
     *
     * @param userId       ID del usuario que se quiere incluir como claim.
     * @param minutesValid Tiempo de validez del token en minutos.
     * @return Token JWT generado.
     */
    String createToken(String userId, int minutesValid);

    /**
     * Verifica la validez de un token JWT.
     *
     * @param token Token JWT recibido.
     * @return Token decodificado si es válido.
     * @throws JWTVerificationException si el token no es válido o está expirado.
     */
    DecodedJWT verifyToken(String token) throws JWTVerificationException;

    /**
     * Extrae el ID de usuario desde un token JWT.
     *
     * @param token Token JWT recibido.
     * @return ID del usuario contenido en el claim.
     */
    String extractUserId(String token);

    /**
     * Obtiene el token JWT desde las cookies o desde el header Authorization.
     *
     * @param request Petición HTTP.
     * @return Token JWT si está presente, o null si no se encuentra.
     */
    String getJwtFromCookies(HttpServletRequest request);
}
