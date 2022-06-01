package com.irdaislakhuafa.springsecuritygraphql.entities.requests.user;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {
    private String id;

    private String name;
    private String email;
    private String password;

    private List<String> roles;
}
