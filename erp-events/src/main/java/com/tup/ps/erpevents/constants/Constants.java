package com.tup.ps.erpevents.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Constants {

    private static String JWT_COOKIE_NAME;
    private static int JWT_EXPIRATION_MINUTES;

    @Autowired
    public Constants(Environment env) {
        JWT_COOKIE_NAME = Objects.requireNonNull(env.getProperty("security.jwt.cookie.name"));
        JWT_EXPIRATION_MINUTES = Integer.parseInt(Objects.requireNonNull(env.getProperty("security.jwt.expiration.minutes")));
    }

    public static String getJwtCookieName() {
        return JWT_COOKIE_NAME;
    }

    public static int getJwtExpirationMinutes() {
        return JWT_EXPIRATION_MINUTES;
    }

}
