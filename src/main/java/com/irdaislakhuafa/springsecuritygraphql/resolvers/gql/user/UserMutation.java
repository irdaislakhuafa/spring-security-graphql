package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.user;

import java.util.NoSuchElementException;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserRequest;
import com.irdaislakhuafa.springsecuritygraphql.services.UserService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "UserMutation")
public class UserMutation {
    private final UserService userService;

    @SchemaMapping(field = "register")
    public User register(@Argument(name = "request") UserRequest request) throws NoSuchElementException {
        var user = this.userService.save(this.userService.toEntity(request));
        return user.orElse(null);
    }
}
