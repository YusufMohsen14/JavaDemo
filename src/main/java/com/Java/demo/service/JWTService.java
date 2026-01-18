package com.Java.demo.service;

import com.Java.demo.model.entity.User;
import com.fasterxml.jackson.databind.DatabindException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JWTService {

    public class JwtService {

        private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // secret key
        private final long expirationMs = 1000 * 60 * 60 * 24; // 24 hours

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


}
