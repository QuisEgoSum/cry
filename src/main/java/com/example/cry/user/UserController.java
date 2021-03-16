package com.example.cry.user;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    public ResponseEntity signup(@Valid @RequestBody UserDTO.CreateDTO user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDTO.UpdateDTO user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }
}
