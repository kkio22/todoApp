package com.example.todoApp.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND_SCHEDULE(404, "Bad Request", "S001", "일정을 찾을 수 없습니다"),

    NOT_FOUND_OWNER(404, "Bad Request", "U001", "사용자 정보가 없습니다."),

    NOT_FOUND_COMMENT(404, "Bad Request", "C001", "댓글을 찾을 수 없습니다"),

    NOT_FOUND_CHILDCOMMENT(404, "Bad Request", "C002", "대댓글을 찾을 수 없습니다"),

    COMMENT_ID_MISMATCH(404, "Bad Request", "C003", "댓글 아이디가 일치하지 않습니다."),

    SCHEDULE_ID_MISMATCH(404, "Bad Request", "S002", "일정 아이디가 일치하지 않습니다.");


    private final int status;
    private final String error;
    private final String code;
    private final String message;
}
