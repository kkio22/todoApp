package com.example.todoApp.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {

    private Long userId;

    private String writerId;

    private String content;
}
