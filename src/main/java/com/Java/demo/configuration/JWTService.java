package com.Java.demo.configuration;

import com.Java.demo.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@AllArgsConstructor
@Service
public class JWTService {

    private final JWTProperties jwtProperties;
    private final Key key;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime())
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        }
    }
