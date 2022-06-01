package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.List;
import java.util.Optional;
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

}
