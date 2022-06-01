package com.irdaislakhuafa.springsecuritygraphql.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Hello {
    private String message;
}
