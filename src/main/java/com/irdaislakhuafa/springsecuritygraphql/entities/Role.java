package com.irdaislakhuafa.springsecuritygraphql.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
