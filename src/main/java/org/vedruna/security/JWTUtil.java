package org.vedruna.security;

import java.time.Duration;
import java.time.Instant;
import io.smallrye.jwt.build.Jwt;
import org.vedruna.model.User;

public class JWTUtil {
    public static String generateToken(User user) {
        Instant now = Instant.now();

        return Jwt.issuer("https://www.linkedin.com/in/pablo-rey-ramos/")
                .subject(user.getId().toString())
                .upn(user.getEmail())
                .groups("USER")
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofHours(6)))
                .claim("employee_id", user.getEmployee().getId())
                .sign();
    }
}
