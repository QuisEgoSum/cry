package com.example.cry.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public UserModel(UserDTO.CreateDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
