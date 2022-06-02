package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.role;

import java.util.List;

import com.irdaislakhuafa.springsecuritygraphql.entities.Role;
import com.irdaislakhuafa.springsecuritygraphql.services.RoleService;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "RoleQuery")
public class RoleQuery {
    private final RoleService roleService;

    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    @SchemaMapping(field = "findAll")
    public List<Role> findAll() {
        var roles = this.roleService.findAll();
        return roles;
    }
}
