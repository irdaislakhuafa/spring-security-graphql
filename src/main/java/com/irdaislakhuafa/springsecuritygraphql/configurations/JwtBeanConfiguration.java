package com.irdaislakhuafa.springsecuritygraphql.configurations;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtBeanConfiguration {
    @Value(value = "${jwt.secret.key}")
    private String secretKey;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(this.secretKey);
    }
}
