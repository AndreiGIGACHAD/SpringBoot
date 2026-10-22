package ru.example.electroshop.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;
import ru.example.electroshop.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository r) {
        repo=r;
    }
    public UserDetails loadUserByUsername(String name) {
        var u=repo.findByUsername(name).orElseThrow(()->new UsernameNotFoundException(name));
        return User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().name()).build();
    }
}
