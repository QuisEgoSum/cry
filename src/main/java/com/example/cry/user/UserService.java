package com.example.cry.user;


import com.example.cry.exeption.BadRequestException;
import com.example.cry.exeption.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public UserModel findUserById(String userId) throws EntityNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserModel createUser(UserDTO.CreateDTO user) throws BadRequestException {
        try {
            return userRepository.saveUser(new UserModel(user));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }

    public UserModel updateUser(String userId, UserDTO.UpdateDTO user) throws BadRequestException {
        try {
            return userRepository.updateUser(userId, user).orElseThrow(() -> new EntityNotFoundException("User not found"));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }
}
