package com.example.cry.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %s was not found", id)));
        return new UserPrincipal(user);
    }
}
