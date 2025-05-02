package com.example.todoApp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor //생성자
public class ScheduleListResponseDto {

    private Long id;

    private String writerId;

    private String title;

    private String content;

    private LocalDateTime updatedAt;

}
