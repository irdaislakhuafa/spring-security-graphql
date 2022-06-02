package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.entities.repositories.UserRepository;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserRequest;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserResponse;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService, BaseService<User, UserRequest> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public Optional<User> save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return Optional.of(Optional.of(this.userRepository.save(entity))).orElse(Optional.empty());
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.of(Optional.of(this.userRepository.save(entity))).orElse(Optional.empty());
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User toEntity(UserRequest request) throws NoSuchElementException {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .roles(this.roleService.findAllByName(request.getRoles()))
                .password(request.getPassword())
                .build();
    }

    @Override
    public List<User> toEntities(List<UserRequest> requests) throws NoSuchElementException {
        return requests.stream().map((r) -> this.toEntity(r)).collect(Collectors.toList());
    }

    public UserResponse toResponse(User entity) {
        var response = UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .password(entity.getPassword())
                .roles(entity.getRoles().stream().map((r) -> r.getName()).collect(Collectors.toList()))
                .build();
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmailEqualsIgnoreCase(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "user with email: " + username.toUpperCase() + " not found"));
    }

    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmailEqualsIgnoreCase(email);
    }
}
