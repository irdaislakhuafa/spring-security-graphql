package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.user;

import java.util.NoSuchElementException;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserRequest;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserResponse;
import com.irdaislakhuafa.springsecuritygraphql.services.RoleService;
import com.irdaislakhuafa.springsecuritygraphql.services.UserService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "UserMutation")
public class UserMutation {
    private final UserService userService;
    private final RoleService roleService;

    @SchemaMapping(field = "register")
    public User register(@Argument(name = "request") UserRequest request) throws NoSuchElementException {
        var user = this.userService.save(this.userService.toEntity(request));
        return user.orElse(null);
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
