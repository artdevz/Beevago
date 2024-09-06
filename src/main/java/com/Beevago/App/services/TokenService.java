package com.Beevago.App.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Beevago.App.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(UserModel user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                .withIssuer("beevago")
                .withSubject(user.getUserEmail())
                .withExpiresAt(getExpirationDate())
                .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o Token.", exception);
        }

    }

    public String validationToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
            .withIssuer("beevago")
            .build()
            .verify(token)
            .getSubject();

        } catch (JWTVerificationException exception) {
            return null;
        }

    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
