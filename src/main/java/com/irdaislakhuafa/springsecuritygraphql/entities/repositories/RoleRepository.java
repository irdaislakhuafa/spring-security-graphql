package com.irdaislakhuafa.springsecuritygraphql.entities.repositories;

import java.util.Optional;

import com.irdaislakhuafa.springsecuritygraphql.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    public Optional<Role> findByNameEqualsIgnoreCase(String name);

    public Optional<Role> findByNameIgnoreCase(String name);
}
