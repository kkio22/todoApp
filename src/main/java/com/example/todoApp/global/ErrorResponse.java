package com.example.todoApp.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status;

    private String error;

    private String code;

    private String message;

}
