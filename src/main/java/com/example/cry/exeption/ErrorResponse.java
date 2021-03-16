package com.example.cry.exeption;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String message;

    public static ErrorResponse defaultError(String errorMessage) {
        return new ErrorResponse(errorMessage);
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
