package com.tup.ps.erpevents.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tup.ps.erpevents.services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    private static final String SECRET_KEY = "secret";
    private static final String ISSUER = "erp_events";
    private static final String JWT_COOKIE_NAME = "jwt-token";

    public String createToken(String userId, int minutesValid) {
        var algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + minutesValid * 60 * 1000))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        var algorithm = Algorithm.HMAC256(SECRET_KEY);
        var verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(token);
    }

    public String extractUserId(String token) {
        var jwt = JWT.decode(token);
        return jwt.getClaim("userId").asString();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token after "Bearer "
        }

        return null;
    }
}

