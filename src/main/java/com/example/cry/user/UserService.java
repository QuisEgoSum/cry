package com.example.cry.user;


import com.example.cry.exeption.BadRequestException;
import com.example.cry.exeption.NotFoundException;
import com.mongodb.MongoWriteException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public UserModel findUserById(String userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserModel createUser(UserCreateDTO user) throws MongoWriteException {
        try {
            return userRepository.saveUser(new UserModel(user));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }

    public UserModel updateUser(String userId, UserUpdateDTO user) {
        try {
            return userRepository.updateUser(userId, user).orElseThrow(() -> new NotFoundException("User not found"));
        } catch (DuplicateKeyException ex) {
            throw new BadRequestException("User with this email address already exists");
        }
    }
}
