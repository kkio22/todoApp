package com.example.todoApp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleWithCommentDto {

    private Long commentId;

    private String content;

    private String writerId;

    private LocalDateTime updateAt;
}
