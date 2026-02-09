package com.Java.demo.security;

import com.Java.demo.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${security.jwt.secret-key}")
    private String secret;

    @Value("${security.jwt.expiration-time}")
    private long AccessExpirationTime;

    @Value("${security.jwt.refresh-expiration-time}")
    private long RefreshExpirationTime;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + AccessExpirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + RefreshExpirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Instant getRefreshTokenExpirationTime() {
        return Instant.now().plusMillis(RefreshExpirationTime);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
