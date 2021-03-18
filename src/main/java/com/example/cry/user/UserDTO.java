package com.example.cry.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @Setter
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

    @Getter
    @AllArgsConstructor
    static public class Signin {
        @NotBlank
        @Size(min = 1, max = 31)
        private String username;

        @NotBlank
        @Size(min = 6, max = 63)
        private String password;
    }

    @AllArgsConstructor
    static public class SigninReponse {

        private String jwt;

        private String id;

        private String username;

        public SigninReponse(String jwt, UserPrincipal user) {
            this.jwt = jwt;
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }
}
