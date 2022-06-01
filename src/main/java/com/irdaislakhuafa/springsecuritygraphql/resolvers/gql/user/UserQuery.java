package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.user;

import java.util.List;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.services.UserService;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "UserQuery")
public class UserQuery {
    private final UserService userService;

    @SchemaMapping(field = "findAll")
    public List<User> findAll() {
        return userService.findAll();
    }
}
