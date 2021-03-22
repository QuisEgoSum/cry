package com.example.cry.user;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;


@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/admin/user/{userId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity findUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/admin/user")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity signup(@Valid @RequestBody UserDTO.CreateAdmin user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @PatchMapping("/admin/user/{userId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDTO.UpdateAdmin user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@Valid @RequestBody UserDTO.Signin credentials) {
        return ResponseEntity.ok(userService.signin(credentials));
    }

    @GetMapping("/admin/users")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<UserModel>> findUsersAdmin(@RequestParam(defaultValue = "USER") UserRoles role) {
        return ResponseEntity.ok(userService.findUsers(role));
    }

    @GetMapping("/user")
    public ResponseEntity findUserInfoMe(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userService.findUserById(user.getId()));
    }

    @PatchMapping("/user")
    public ResponseEntity updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody UserDTO.UpdateUser user) {
        return ResponseEntity.ok(userService.updateUser(userPrincipal.getId(), user));
    }

    @GetMapping("/users")
    public ResponseEntity findUsers() {
        return ResponseEntity.ok(userService.findUsers(UserRoles.USER));
    }

    @PutMapping("/user/{targetUserId}/subscribe")
    public ResponseEntity subscribeUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("targetUserId") String targetUserId) {
        return ResponseEntity.ok(userService.subscribeUser(userPrincipal.getId(), targetUserId));
    }

    @PutMapping("/user/{targetUserId}/unsubscribe")
    public ResponseEntity unsubscribeUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("targetUserId") String targetUserId) {
        return ResponseEntity.ok(userService.unsubscribeUser(userPrincipal.getId(), targetUserId));
    }
}
