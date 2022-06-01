package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.hello;

import com.irdaislakhuafa.springsecuritygraphql.entities.Hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    // @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @SchemaMapping(field = "sayHello")
    public Hello sayHello() {
        return Hello.builder().message("Hi my name is irda islakhu afa").build();
    }
}
