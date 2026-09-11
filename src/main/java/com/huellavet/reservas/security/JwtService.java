package com.huellavet.reservas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generarToken(String subject, String rol, Long id) {
        return Jwts.builder()
                .subject(subject) // normalmente el correo/email
                .claim("rol", rol)
                .claim("id", id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(obtenerClave(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerSubject(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public String extraerRol(String token) {
        return extraerClaim(token, claims -> claims.get("rol", String.class));
    }

    public Long extraerId(String token) {
        return extraerClaim(token, claims -> claims.get("id", Long.class));
    }

    public boolean esTokenValido(String token, String subjectEsperado) {
        String subject = extraerSubject(token);
        return subject.equals(subjectEsperado) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}