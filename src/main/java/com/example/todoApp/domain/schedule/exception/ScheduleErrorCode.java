package com.example.todoApp.domain.schedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode {

    NOT_FOUND_SCHEDULE(404, "Bad Request", "S001", "일정을 찾을 수 없습니다"),

    NOT_FOUND_USER(404, "Bad Request", "U001", "사용자 정보가 없습니다.");


    private final int status;
    private final String error;
    private final String code;
    private final String message;
}
