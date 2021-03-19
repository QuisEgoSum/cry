package com.example.cry.issue;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("issues")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueModel {

    @Id
    private String id;

    @Setter
    private String title;

    @Setter
    private String description;

    private String belongsTo;

    @Setter
    private Integer amountOfWhining = 0;

    @Setter
    private Boolean open = true;

    private Set<String> whiners;

    private String closedBy;

    public IssueModel(IssueDTO.Create issue, String belongsTo) {
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.belongsTo = belongsTo;
    }
}
