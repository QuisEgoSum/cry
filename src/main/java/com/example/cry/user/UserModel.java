package com.example.cry.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class UserModel {

    @Id
    private String id;

    @Setter
    @Indexed(unique = true)
    private String username;

    @Setter
    @JsonIgnore
    private String password;

    private UserRoles role = UserRoles.ADMIN;

    private Set<String> subscriptions;

    public UserModel(UserDTO.CreateAdmin user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
