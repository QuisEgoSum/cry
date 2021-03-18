package com.example.cry.issue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
}
