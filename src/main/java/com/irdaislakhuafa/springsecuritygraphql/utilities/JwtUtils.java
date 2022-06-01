package com.irdaislakhuafa.springsecuritygraphql.utilities;

import java.util.Date;

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

    private Claims generateClaims(User user) {
        log.info("generate claims");

        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("roles", user.getAuthorities());

        log.info("success generate claims");
        return claims;
    }

    public String generateTokenString(User user) {
        var claims = this.generateClaims(user);

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

    public Claims getClaimsFromTokenString(String tokenString) throws Exception {
        log.info("get claims from token string");
        var claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJwt(tokenString)
                .getBody();
        log.info("success get claims");
        return claims;
    }
}
