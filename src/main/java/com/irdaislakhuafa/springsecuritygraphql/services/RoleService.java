package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.springsecuritygraphql.entities.Role;
import com.irdaislakhuafa.springsecuritygraphql.entities.repositories.RoleRepository;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.role.RoleRequest;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements BaseService<Role, RoleRequest> {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> save(Role entity) {
        return Optional.of(Optional.of(this.roleRepository.save(entity))).orElse(Optional.empty());
    }

    @Override
    public Optional<Role> update(Role entity) {
        return this.save(entity);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role toEntity(RoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .build();
    }

    @Override
    public List<Role> toEntities(List<RoleRequest> requests) {
        return requests.stream().map((r) -> this.toEntity(r)).collect(Collectors.toList());
    }

    public List<Role> findAllByName(String... names) throws NoSuchElementException {
        var list = new ArrayList<Role>();
        for (var name : names) {
            var role = roleRepository.findByNameEqualsIgnoreCase(name);
            if (!role.isPresent()) {
                var newRole = Role.builder()
                        .name(name)
                        .build();
                role = this.save(newRole);
            }
            list.add(role.get());
        }
        return list;
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByNameEqualsIgnoreCase(name);
    }
}
