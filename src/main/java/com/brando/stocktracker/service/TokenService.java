package com.brando.stocktracker.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brando.stocktracker.config.JWTUserData;
import com.brando.stocktracker.entity.Role;
import com.brando.stocktracker.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {
    @Value("${security.secretLoginKey}")
    private String secrete;

    public String generateToken(User user){
        System.out.println(secrete);
        Algorithm algorithm =Algorithm.HMAC256(secrete);

        return JWT.create()
                .withIssuer("stocktracker-api")
                .withClaim("userID", user.getId())
                .withSubject(user.getEmail())
                .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validarToken(String token) {
        try {
            Algorithm algorithm =Algorithm.HMAC256(secrete);

            DecodedJWT decode = JWT.require(algorithm)
                    .withIssuer("stocktracker-api")
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                            .email(decode.getSubject())
                            .userId(decode.getClaim("userId").asString())
                            .roles(decode.getClaim("roles").asList(Role.class))
                            .build());

        } catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
