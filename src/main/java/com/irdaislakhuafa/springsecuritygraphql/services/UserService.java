package com.irdaislakhuafa.springsecuritygraphql.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.springsecuritygraphql.entities.User;
import com.irdaislakhuafa.springsecuritygraphql.entities.repositories.UserRepository;
import com.irdaislakhuafa.springsecuritygraphql.entities.requests.user.UserRequest;

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
    public User toEntity(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    @Override
    public List<User> toEntities(List<UserRequest> requests) {
        return requests.stream().map((r) -> this.toEntity(r)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmailEqualsIgnoreCase(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "user with email: " + username.toUpperCase() + " not found"));
    }

}
