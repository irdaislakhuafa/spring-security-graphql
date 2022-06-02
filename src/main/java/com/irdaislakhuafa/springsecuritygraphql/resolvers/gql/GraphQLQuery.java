package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql;

import com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello.HelloQuery;
import com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.role.RoleQuery;
import com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.user.UserQuery;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "Query")
public class GraphQLQuery {
    private final HelloQuery helloQuery;
    private final RoleQuery roleQuery;
    private final UserQuery userQuery;

    @SchemaMapping(field = "hello")
    public HelloQuery hello() {
        return helloQuery;
    }

    @SchemaMapping(field = "role")
    public RoleQuery roleQuery() {
        return roleQuery;
    }

    @SchemaMapping(field = "user")
    public UserQuery userQuery() {
        return userQuery;
    }
}
