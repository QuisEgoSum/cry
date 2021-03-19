package com.example.cry.issue;


import com.example.cry.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;

    @PostMapping("/issue")
    public ResponseEntity createIssue(@Valid @RequestBody IssueDTO.Create issue, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(201).body(issueService.createIssue(issue, user.getId()));
    }

    @PatchMapping("/issue/{issueId}/whine")
    public ResponseEntity issueWhine(@PathVariable("issueId") String issueId, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(issueService.issueWhine(issueId, user.getId()));
    }

    @PatchMapping("/issue/{issueId}/close")
    public ResponseEntity closeIssue(@PathVariable("issueId") String issueId, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(issueService.closeIssue(issueId, user.getId()));
    }

    @GetMapping("/issue/user/{userId}")
    public ResponseEntity getUserIssues(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(issueService.findUserIssues(userId));
    }
}
