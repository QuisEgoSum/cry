package com.example.cry.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank
    @Size(min = 1, max = 31)
    private String username;

    @NotBlank
    @Size(min = 6, max = 63)
    private String password;
}
