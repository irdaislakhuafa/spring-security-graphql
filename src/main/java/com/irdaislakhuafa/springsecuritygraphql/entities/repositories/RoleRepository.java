package com.irdaislakhuafa.springsecuritygraphql.entities.repositories;

import com.irdaislakhuafa.springsecuritygraphql.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import graphql.com.google.common.base.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    public Optional<Role> findByNameEqualsIgnoreCase(String name);
}
