package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello;

import com.irdaislakhuafa.springsecuritygraphql.entities.Hello;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.hello.HelloRequest;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloMutation")
public class HelloMutation {
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @SchemaMapping(field = "sayHello")
    public Hello sayHello(@Argument(name = "request") HelloRequest request) {
        return Hello.builder().message("Hi " + request.getTo().toUpperCase() + " my name is irda islakhu afa").build();
    }
}
