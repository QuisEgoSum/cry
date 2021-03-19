package com.example.cry.issue;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    private ObjectId belongsTo;

    @Setter
    private Integer amountOfWhining = 0;

    @Setter
    private Boolean open = true;

    private ArrayList<ObjectId> whiners = new ArrayList<>();

    public List<String> getWhiners() {
        return this.whiners.stream().map(ObjectId::toString).collect(Collectors.toList());
    }

    public String getBelongsTo() {
        return this.belongsTo.toString();
    }

    public IssueModel(IssueDTO.Create issue, String belongsTo) {
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.belongsTo = new ObjectId(belongsTo);
    }
}
