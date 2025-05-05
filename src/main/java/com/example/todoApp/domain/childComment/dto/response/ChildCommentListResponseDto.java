package com.example.todoApp.domain.childComment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChildCommentListResponseDto {

    private Long id;

    private String writerId;

    private String content;

    private LocalDateTime updateAt;
}
