package com.irdaislakhuafa.springsecuritygraphql.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // enable @PreAuthorize
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable login form
                .csrf().disable()

                .authorizeRequests().antMatchers("/graphiql", "/graphql", "/vendor/**").permitAll()
                .anyRequest().authenticated()

        // end
        ;

        // disable session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // .authorizeRequests().antMatchers("/graphql", "/graphiql").permitAll();
        return http.build();
    }

    // @Bean
    // public EmbeddedLdapServerContextSourceFactoryBean factoryBean() {
    // var ldapServer =
    // EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
    // ldapServer.setPort(0);
    // return ldapServer;
    // }

    // @Bean
    // public AuthenticationManager
    // authenticationManager(AuthenticationConfiguration auth) {
    // var authManager = auth.getAuthenticationManager();
    // // authManager.
    // }
}
