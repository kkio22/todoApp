package com.example.todoApp.domain.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ScheduleCreateRequestDto {

    private Long userId;

    private String writerId;

    private String title;

    private String content;

}
