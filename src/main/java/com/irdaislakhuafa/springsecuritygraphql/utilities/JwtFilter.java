package com.irdaislakhuafa.springsecuritygraphql.utilities;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irdaislakhuafa.springsecuritygraphql.services.UserService;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(value = 1)
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Starting jwt filter \t\t:: " + request.getRequestURI());

        log.info("Get Authorization header \t\t:: " + request.getRequestURI());
        var authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            log.info("Authorization header is exists \t:: " + request.getRequestURI());

            try {
                log.info("Get claims from token string \t:: " + request.getRequestURI());
                var tokenString = authHeader.substring(BEARER.length());
                var claims = jwtUtils.getClaimsFromTokenString(tokenString);
                log.info("Success get claims from token string \t:: " + request.getRequestURI());

                log.info("Validate token string \t:: " + request.getRequestURI());
                var user = this.userService.findById(claims.get("userId", String.class));
                if (!user.isPresent()) {
                    throw new UsernameNotFoundException(
                            "user with id: " + claims.get("userId", String.class) + " not found");
                }

                if (jwtUtils.validateTokenString(tokenString, user.get())) {
                    log.info("Token string is valid");

                    log.info("Starting authentication user");
                    var authUser = new UsernamePasswordAuthenticationToken(
                            user.get().getEmail(),
                            user.get().getPassword(),
                            user.get().getAuthorities());

                    log.info("Add user details in auth user");
                    authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    log.info("Register to security context");
                    SecurityContextHolder.getContext().setAuthentication(authUser);
                }

                log.debug("Token Claims : " + claims.get("userId", String.class));

            } catch (Exception e) {
                request.setAttribute("requestError", e.getMessage());
                e.printStackTrace();
            }

        } else {
            log.error("Invalid Authorization header or Empty Authorization header");
        }

        filterChain.doFilter(request, response);
        log.info("Successfully passed the jwt filter :: " + request.getRequestURI());
    }

}
