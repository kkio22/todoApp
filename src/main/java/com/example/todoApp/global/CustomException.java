package com.example.todoApp.global;

import com.example.todoApp.domain.comment.exception.CommentErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

}
