package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.user;

import java.util.HashMap;
import java.util.NoSuchElementException;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserRequest;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserResponse;
import com.irdaislakhuafa.springsecuritygraphql.services.RoleService;
import com.irdaislakhuafa.springsecuritygraphql.services.UserService;
import com.irdaislakhuafa.springsecuritygraphql.utilities.JwtUtils;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "UserMutation")
public class UserMutation {
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @SchemaMapping(field = "register")
    public User register(@Argument(name = "request") UserRequest request) throws NoSuchElementException {
        var user = this.userService.save(this.userService.toEntity(request));
        return user.orElse(null);
    }

    @SchemaMapping(field = "login")
    public Object login(@Argument(name = "email") String email, @Argument(name = "password") String password) {
        log.info("entering login resolver");
        var response = new HashMap<>();
        try {
            log.info("find user by email");
            User user = this.userService.findByEmail(email)
                    .orElseThrow(() -> {
                        final var message = "user with email: " + email + " not found";
                        log.error(message);
                        return new UsernameNotFoundException(message);
                    });
            log.info("user is exists");

            log.info("authenticate user");
            var auth = new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
            authenticationManager.authenticate(auth);
            log.info("success authenticate user");

            final var tokenString = jwtUtils.generateTokenString(user);
            response.put("error", null);
            response.put("token", tokenString);

        } catch (BadCredentialsException e) {
            response.put("error", "invalid username or password");
            response.put("token", null);
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("token", null);
        }

        return response;
    }

    @SchemaMapping(field = "addRole")
    public UserResponse addRole(@Argument(name = "userId") String userId, @Argument(name = "role") String roleName) {
        var user = userService.findById(userId);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("user with id: " + userId + " not found");
        }

        var role = roleService.findByName(roleName);
        if (!role.isPresent()) {
            throw new NoSuchElementException("role: " + roleName.toUpperCase() + " not found");
        }

        user.get().getRoles().add(role.get());
        user = userService.update(user.get());
        var userResponse = userService.toResponse(user.get());
        return userResponse;
    }
}
