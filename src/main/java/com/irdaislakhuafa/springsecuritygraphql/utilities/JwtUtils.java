package com.irdaislakhuafa.springsecuritygraphql.utilities;

import java.util.*;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
    @Value(value = "${jwt.secret.key}")
    private String secretKey;

    @Value(value = "${jwt.token.expired.in.minute}")
    private long tokenExpiredInMinute;

    private Map<String, Object> generateClaims(User user) {
        log.info("generate claims");

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("roles", user.getAuthorities());

        log.info("success generate claims");
        return claims;
    }

    public String generateTokenString(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("roles", user.getAuthorities());

        log.info("success generate claims");

        log.info("generate token string");
        var tokenString = Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ((1000L * 60) * tokenExpiredInMinute)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.info("success generate token string");

        return tokenString;
    }

    public Claims getClaimsFromTokenString(String tokenString) {
        log.info("get claims from token string");
        var claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(tokenString)
                .getBody();
        log.info("success get claims");
        return claims;
    }

    public boolean isExpired(String tokenString) throws Exception {
        log.info("check is token expired");
        if (this.getClaimsFromTokenString(tokenString)
                .get("exp", Date.class)
                .after(new Date(System.currentTimeMillis()))) {

            log.info("token is expired");
            return false;
        } else {
            log.info("token is valid");
            return true;
        }

    }

    public boolean validateTokenString(String tokenString, User user) throws Exception {
        var claims = this.getClaimsFromTokenString(tokenString);
        return (claims.get("userId", String.class).equalsIgnoreCase(user.getId())) && !this.isExpired(tokenString);
    }
}
