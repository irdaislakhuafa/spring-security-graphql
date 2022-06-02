package com.irdaislakhuafa.springsecuritygraphql.entities.requests.role;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequest {
    private String name;
}
