package com.example.cry.issue;


import com.example.cry.exeption.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
public class IssueService {

    IssueRepository issueRepository;

    public IssueModel createIssue(IssueDTO.Create issue, String belongsTo) {
        return issueRepository.saveIssue(new IssueModel(issue, belongsTo));
    }

    public IssueModel issueWhine(String issueId, String whinerId) {
        return issueRepository.addWhine(issueId, whinerId)
                .orElseThrow(() -> new EntityNotFoundException("There is no such issue"));
    }

    public IssueModel closeIssue(String issueId, String userId) {
        return issueRepository.closeIssue(issueId, userId)
                .orElseThrow(() -> new EntityNotFoundException("There is no such problem or it does not belong to you"));
    }

    public List<IssueDTO.IssuePopulate> findUserIssues(String userId) {
        return issueRepository.findUserIssues(userId);
    }
}
