package com.example.cry.user;


import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public Optional<UserModel> updateUser(String userId, UserDTO.UpdateAdmin user) {

        Update update = new Update();

        if (user.getUsername() != null)
            update.set("username", user.getUsername());
        if (user.getPassword() != null)
            update.set("password", user.getPassword());
        if (user.getRole() != null)
            update.set("role", user.getRole());

        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(userId)),
                update,
                new FindAndModifyOptions().returnNew(true),
                UserModel.class));
    }

    public Optional<UserModel> updateUser(String userId, UserDTO.UpdateUser user) {

        Update update = new Update();

        if (user.getUsername() != null)
            update.set("username", user.getUsername());
        if (user.getPassword() != null)
            update.set("password", user.getPassword());

        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(userId)),
                update,
                new FindAndModifyOptions().returnNew(true),
                UserModel.class));
    }


    public Optional<UserModel> findByUsername(String username) {
        return Optional.ofNullable(mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), UserModel.class));
    }

    public List<UserModel> findUsers(UserRoles role) {

        Query query = new Query(Criteria.where("role").is(role.name()));

        query.fields().exclude("subscriptions");

        return mongoTemplate.find(query, UserModel.class);
    }

    public Optional<UserModel> userExistNoAdmin(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId).and("role").ne(UserRoles.ADMIN.name()));
        query.fields().include("_id");
        return Optional.ofNullable(mongoTemplate.findOne(query, UserModel.class));
    }

    public Optional<UserModel> subscribeUser(String userId, String targetUserId) {
        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(userId)),
                new Update().addToSet("subscriptions").value(new ObjectId(targetUserId)),
                new FindAndModifyOptions().returnNew(true),
                UserModel.class
        ));
    }

    public Optional<UserModel> unsubscribeUser(String userId, String targetUserId) {
        return  Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(userId)),
                new Update().pull("subscriptions", new ObjectId(targetUserId)),
                new FindAndModifyOptions().returnNew(true),
                UserModel.class
        ));
    }
}
