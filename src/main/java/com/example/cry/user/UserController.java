package com.example.cry.user;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity findUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/user")
    public ResponseEntity signup(@Valid @RequestBody UserDTO.CreateDTO user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDTO.UpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@Valid @RequestBody UserDTO.Signin credentials) {
        return ResponseEntity.ok(userService.signin(credentials));
    }
}
