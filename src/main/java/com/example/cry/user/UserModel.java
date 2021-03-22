package com.example.cry.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private List<ObjectId> subscriptions;

    public List<String> getSubscriptions() {
        return this.subscriptions == null ? null : this.subscriptions.stream().map(ObjectId::toString).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<ObjectId> getSubscriptionsObjectId() {
        return this.subscriptions;
    }

    public UserModel(UserDTO.CreateAdmin user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
