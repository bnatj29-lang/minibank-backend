package com.minibank.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtService(
            @Value("${minibank.jwt.secret}") String segredo,
            @Value("${minibank.jwt.expiracao-ms}") long expiracaoMs) {
        if (segredo == null || segredo.length() < 32) {
            throw new IllegalArgumentException("MINIBANK_JWT_SECRET deve ter pelo menos 32 caracteres.");
        }
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMs = expiracaoMs;
    }

    public String gerarToken(Long responsavelId) {
        Date agora = new Date();
        return Jwts.builder()
                .subject(String.valueOf(responsavelId))
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + expiracaoMs))
                .signWith(chave)
                .compact();
    }

    public String extrairResponsavelId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
