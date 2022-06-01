package com.irdaislakhuafa.springsecuritygraphql.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JwtBeanConfiguration {
    @Value(value = "${jwt.secret.key}")
    private String secretKey;

    @Value(value = "${jwt.token.expired.in.minute}")
    private long tokenExpiredInMinute;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(this.secretKey);
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm) {
        return JWT
                .require(algorithm)
                .acceptIssuedAt(System.currentTimeMillis())
                .acceptExpiresAt(System.currentTimeMillis() + (1000L * 60 * tokenExpiredInMinute))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
