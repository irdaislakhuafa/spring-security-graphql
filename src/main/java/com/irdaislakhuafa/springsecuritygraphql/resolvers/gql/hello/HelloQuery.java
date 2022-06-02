package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello;

import com.irdaislakhuafa.springsecuritygraphql.entities.Hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @SchemaMapping(field = "sayHello")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public Hello sayHello() {
        return Hello.builder().message("Hi my name is irda islakhu afa").build();
    }
}
