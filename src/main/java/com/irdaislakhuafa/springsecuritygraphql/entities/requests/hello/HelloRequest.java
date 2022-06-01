package com.irdaislakhuafa.springsecuritygraphql.entities.requests.hello;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HelloRequest {
    private String to;
}
