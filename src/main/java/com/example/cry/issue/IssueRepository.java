package com.example.cry.issue;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@AllArgsConstructor
public class IssueRepository {

    private MongoTemplate mongoTemplate;

    public IssueModel saveIssue(IssueModel issue) throws DuplicateKeyException {
        return mongoTemplate.save(issue);
    }

    public Optional<IssueModel> addWhine(String issueId, String whinerId) {
        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(
                        Criteria
                                .where("_id").is(issueId)
                                .and("open").is(true)
                ),
                new Update()
                        .addToSet("whiners", new ObjectId(whinerId))
                        .inc("amountOfWhining", 1),
                new FindAndModifyOptions()
                        .returnNew(true),
                IssueModel.class
        ));
    }

    public Optional<IssueModel> closeIssue(String issueId, String userId) {
        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(issueId).and("open").is(true).and("belongsTo").is(userId)),
                new Update().set("open", false),
                new FindAndModifyOptions()
                    .returnNew(true),
                IssueModel.class
        ));
    }

    public List<IssueDTO.IssuePopulate> findUserIssues(String userId) {
        /**
         * db.getCollection('issues').aggregate([
         *     {$match: {"belongsTo": ObjectId('60544afaf218421c1b69ac1d')}},
         *     {$lookup: {
         *             from: 'users',
         *             localField: 'belongsTo',
         *             as: 'belongsTo',
         *             foreignField: '_id'
         *         }
         *     },
         *     {$unwind: '$belongsTo'},
         *     {$lookup: {
         *             from: 'users',
         *             localField: 'whiners',
         *             as: 'whiners',
         *             foreignField: '_id'
         *         }
         *     }
         * ])
         */
        return mongoTemplate
                .aggregate(
                        newAggregation(
                            match(Criteria.where("belongsTo").is(new ObjectId(userId))),
                            lookup("users", "belongsTo", "_id", "belongsTo"),
                            unwind("belongsTo"),
                            lookup("users", "whiners", "_id", "whiners")
                        ),
                        IssueModel.class,
                        IssueDTO.IssuePopulate.class
                ).getMappedResults();
    }

    public List<IssueDTO.IssuePopulate> findUsersIssues(List<ObjectId> userIds) {
        return mongoTemplate
                .aggregate(
                        newAggregation(
                                match(Criteria.where("belongsTo").in(userIds)),
                                lookup("users", "belongsTo", "_id", "belongsTo"),
                                unwind("belongsTo"),
                                lookup("users", "whiners", "_id", "whiners")
                        ),
                        IssueModel.class,
                        IssueDTO.IssuePopulate.class
                ).getMappedResults();
    }
}
