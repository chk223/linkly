package com.example.linkly.util.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Slf4j
@Component
public class JwtUtil {
    private final String accessTokenSecret = "your-256-bit-secret-for-access-token";
    private final String refreshTokenSecret = "your-256-bit-secret-for-refresh-token";
    private final long accessTokenValidityInMilliseconds = 3600000; // 1시간
    private final long refreshTokenValidityInMilliseconds = 604800000; // 7일

    private final Key accessTokenKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
    private final Key refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());

    // Access Token 생성
    public String generateAccessToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityInMilliseconds))
                .signWith(accessTokenKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidityInMilliseconds))
                .signWith(refreshTokenKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Access Token 검증
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Access Token expired", e);
            throw e; // 필터나 컨트롤러에서 처리 가능하도록 던짐
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid Access Token", e);
            return false;
        }
    }

    // Refresh Token 검증
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Access Token에서 사용자 이메일 추출
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessTokenKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
