package com.irdaislakhuafa.springsecuritygraphql.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class GraphQLScalarsConfiguration {
    @Bean
    public RuntimeWiringConfigurer object() {
        return (runtime) -> runtime.scalar(ExtendedScalars.Object);
    }
}
