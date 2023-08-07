package com.clinica.api.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    private final Duration tokenValidity;
    private final Algorithm hmac512;
    private final JWTVerifier verifier;

    public JwtTokenService(@Value("${jwt.secret}") final String secret,
                           @Value("${jwt.token.validity.duration}") final Integer tokenDuration,
                           @Value("${jwt.token.validity.unit}") final String durationUnit) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
        this.tokenValidity = Duration.of(tokenDuration, ChronoUnit.valueOf(durationUnit));
    }

    public String generateToken(String email, Integer userId) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(email)
                .withClaim("userid", userId)
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(tokenValidity.toMillis()))
                .sign(this.hmac512);
    }

    public String validateTokenAndGetEmail(final String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            //log.warn("token invalid: {}", verificationEx.getMessage());
            return null;
        }
    }

}