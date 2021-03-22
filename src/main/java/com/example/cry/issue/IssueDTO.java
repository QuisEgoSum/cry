package com.example.cry.issue;


import com.example.cry.user.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IssueDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Create {

        @NotBlank
        @Size(max = 63)
        private String title;

        @Size(max = 128)
        private String description;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Update {

        @Size(max = 63)
        private String title;

        @Size(max = 128)
        private String description;
    }

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class IssuePopulate {

        private String id;

        private String title;

        private String description;

        private UserInfo belongsTo;

        private Integer amountOfWhining = 0;

        private Boolean open = true;

        private List<UserInfo> whiners;

        @Data
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        static public class UserInfo {
            private String id;
            private String username;
        }
    }
}
