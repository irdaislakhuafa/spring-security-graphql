package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql;

import com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello.HelloMutation;
import com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.role.RoleMutation;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "Mutation")
public class GraphQLMutation {
    private final HelloMutation helloMutation;
    private final RoleMutation roleMutation;

    @SchemaMapping(field = "hello")
    public HelloMutation hello() {
        return helloMutation;
    }

    @SchemaMapping(field = "role")
    public RoleMutation roleMutation() {
        return roleMutation;
    }
}
