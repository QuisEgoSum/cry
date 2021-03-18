package com.example.cry.issue;

import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
                        .addToSet("whiners", whinerId)
                        .inc("amountOfWhining", 1),
                new FindAndModifyOptions()
                        .returnNew(true),
                IssueModel.class
        ));
    }

    public Optional<IssueModel> closeIssue(String issueId, String userId) {
        return Optional.ofNullable(mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(issueId).and("open").is(true)),
                new Update()
                        .set("open", false)
                        .set("closedBy", userId),
                new FindAndModifyOptions()
                    .returnNew(true),
                IssueModel.class
        ));
    }
}
