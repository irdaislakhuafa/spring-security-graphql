package com.irdaislakhuafa.springsecuritygraphql.configurations;

import com.irdaislakhuafa.springsecuritygraphql.services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // enable @PreAuthorize
public class SecurityConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable login form
                .csrf().disable()

                // permit some url
                .authorizeRequests().antMatchers("/graphiql", "/graphql", "/vendor/**").permitAll()
                // authenticate other url
                .anyRequest().authenticated()

                // set authentication provider
                .and().authenticationProvider(new DaoAuthenticationProvider() {
                    {
                        setPasswordEncoder(passwordEncoder);
                        setUserDetailsService(userService);
                    }
                })

        // end
        ;

        // disable session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // .authorizeRequests().antMatchers("/graphql", "/graphiql").permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        var authManager = auth.getAuthenticationManager();
        return authManager;
    }
}
