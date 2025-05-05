package com.example.todoApp.domain.childComment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChildCommentUpdateResponseDto {

    private Long scheduleId;

    private Long commentId;

    private Long id;

    private String writerId;

    private String content;

    private LocalDateTime updatedAt;
}
