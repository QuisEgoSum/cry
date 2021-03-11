package com.example.cry.user;


import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private MongoTemplate mongoTemplate;

    public Optional<UserModel> findById(String userId) {
        return Optional.ofNullable(mongoTemplate.findById(userId, UserModel.class));
    }

    public UserModel saveUser(UserModel user) {
        return mongoTemplate.save(user);
    }

    public Optional<UserModel> updateUser(String userId, UserUpdateDTO user) {

        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update();
        FindAndModifyOptions options = new FindAndModifyOptions();

        options.returnNew(true);

        if (user.getUsername() != null) {
            update.set("username", user.getUsername());
        }
        if (user.getPassword() != null) {
            update.set("password", user.getPassword());
        }

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, UserModel.class));
    }
}
