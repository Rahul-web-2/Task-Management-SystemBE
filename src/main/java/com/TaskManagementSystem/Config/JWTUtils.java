package com.TaskManagementSystem.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class JWTUtils {

    private final JwtConfig jwtConfig;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    // Generate Token
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ jwtConfig.getExpiration() * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Extract Email
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    //Validate Token
    public boolean isValid(String token){
        try{
            extractClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Extract Claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
