package com.irdaislakhuafa.springsecuritygraphql.resolvers.gql.role;

import com.irdaislakhuafa.springsecuritygraphql.entities.Role;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.role.RoleRequest;
import com.irdaislakhuafa.springsecuritygraphql.services.RoleService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "RoleMutation")
public class RoleMutation {
    private final RoleService roleService;

    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @SchemaMapping(field = "new")
    public Role newRole(@Argument(name = "request") RoleRequest request) throws Exception {
        var role = this.roleService.save(this.roleService.toEntity(request));
        return role.orElse(null);
    }
}
