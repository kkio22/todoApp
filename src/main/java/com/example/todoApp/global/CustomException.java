package com.example.todoApp.global;

import com.example.todoApp.domain.schedule.exception.ScheduleErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ScheduleErrorCode scheduleErrorCode;


    public CustomException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode.getMessage());
        this.scheduleErrorCode=scheduleErrorCode;
    }
}
