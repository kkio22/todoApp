package com.example.todoApp.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentCreateResponseDto {

    private Long userId;

    private Long id;

    private String writerId;

    private String content;

    private LocalDateTime updatedAt;

}
