package com.example.cry.whimper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("whimper")
public class WhimperModel {

    @Id
    private String id;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private ObjectId Owner;

}
