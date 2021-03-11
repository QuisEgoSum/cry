package com.example.cry.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity findUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping
    public ResponseEntity signup(@Valid @RequestBody UserCreateDTO user) {
        System.out.println("kek");
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserUpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }
}
