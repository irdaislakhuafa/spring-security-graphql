package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello;

import com.irdaislakhuafa.springsecuritygraphql.entities.Hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @SchemaMapping(field = "sayHello")
    public Hello sayHello() {
        return Hello.builder().message("Hi my name is irda islakhu afa").build();
    }
}
