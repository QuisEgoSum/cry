package com.example.cry.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CreateDTO {

        @NotBlank
        @Size(min = 1, max = 31)
        private String username;

        @NotBlank
        @Size(min = 6, max = 63)
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class UpdateDTO {

        @Size(min = 1, max = 31)
        private String username;

        @Size(min = 6, max = 63)
        private String password;
    }
}
