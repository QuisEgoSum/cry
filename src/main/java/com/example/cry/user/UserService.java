package com.example.cry.user;


import com.example.cry.exeption.BadRequestException;
import com.example.cry.exeption.EntityNotFoundException;
import com.example.cry.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    PasswordEncoder passwordEncoder;

    public UserModel findUserById(String userId) throws EntityNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserModel createUser(UserDTO.CreateAdmin user) throws BadRequestException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.saveUser(new UserModel(user));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }

    public UserModel updateUser(String userId, UserDTO.UpdateAdmin user) throws BadRequestException {
        try {
            if (user.getPassword() != null)
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.updateUser(userId, user).orElseThrow(() -> new EntityNotFoundException("User not found"));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }

    public UserModel updateUser(String userId, UserDTO.UpdateUser user) throws BadRequestException {
        try {
            if (user.getPassword() != null)
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.updateUser(userId, user).orElseThrow(() -> new EntityNotFoundException("User not found"));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }

    public UserDTO.SigninReponse signin(UserDTO.Signin credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return new UserDTO.SigninReponse(jwt, principal);
    }

    public List<UserModel> findUsers(UserRoles role) {
        return userRepository.findUsers(role);
    }

    public UserModel subscribeUser(String userId, String targetUserId) {

        userRepository.userExistNoAdmin(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userRepository.subscribeUser(userId, targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserModel unsubscribeUser(String userId, String targetUserId) {
        return userRepository.unsubscribeUser(userId, targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
