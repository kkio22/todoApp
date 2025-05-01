package com.example.todoApp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter//프론트에 값 빼기위해
@AllArgsConstructor //생성자
public class ScheduleResponseDto {

    private Long userId;

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

}
