package com.example.todoApp.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequestDto {

    private Long userId;

    private String content;


}
