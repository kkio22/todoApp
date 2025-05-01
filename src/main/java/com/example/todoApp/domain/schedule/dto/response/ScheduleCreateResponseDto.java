package com.example.todoApp.domain.schedule.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ScheduleCreateResponseDto {

    private Long userId;

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;
}
