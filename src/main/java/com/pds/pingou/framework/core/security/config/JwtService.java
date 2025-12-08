package com.pds.pingou.framework.core.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Serviço responsável pela geração e validação de tokens JWT.
 * 
 * Esta classe faz parte do framework de segurança e fornece funcionalidades
 * para criação, validação e extração de informações de tokens JWT.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secretKey;
    
    @Value("${api.security.token.expiration}")
    private long jwtExpiration;
    
    @Value("${api.security.token.refresh-token.expiration}")
    private long refreshExpiration;
    
    @Value("${api.security.token.refresh-token.secret}")
    private String refreshSecretKey;

    /**
     * Extrai o username (email) do token JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject, getSignKey());
    }

    /**
     * Gera um token JWT para o usuário.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Gera um token JWT com claims extras.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration, getSignKey());
    }

    /**
     * Valida se o token é válido para o usuário.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, getSignKey());
    }

    /**
     * Extrai o username do refresh token.
     */
    public String extractUsernameFromRefreshToken(String token) {
        return extractClaim(token, Claims::getSubject, getSignKeyRefresh());
    }

    /**
     * Gera um refresh token para o usuário.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration, getSignKeyRefresh());
    }

    /**
     * Valida se o refresh token é válido.
     */
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromRefreshToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, getSignKeyRefresh());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration, SecretKey key) {
        extraClaims.put("role", userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("USER"));

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    private boolean isTokenExpired(String token, SecretKey key) {
        return extractExpiration(token, key).before(new Date());
    }

    private Date extractExpiration(String token, SecretKey key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSignKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
