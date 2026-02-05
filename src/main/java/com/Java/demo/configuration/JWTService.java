package com.Java.demo.configuration;

import com.Java.demo.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class JWTService {

        private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        private final long expirationMs = 1000 * 60 * 60 * 24;

        public String generateToken(User user) {

            Map<String, Object> claims = new HashMap<>();
            claims.put("firstName", user.getFirstName());
            claims.put("lastName", user.getLastName());

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                    .signWith(key)
                    .compact();
        }
    }
