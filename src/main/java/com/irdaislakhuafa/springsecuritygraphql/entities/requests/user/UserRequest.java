package com.irdaislakhuafa.springsecuritygraphql.entities.requests.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String[] roles;
}
