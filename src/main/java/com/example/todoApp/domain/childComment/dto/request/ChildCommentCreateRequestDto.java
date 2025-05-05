package com.example.todoApp.domain.childComment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildCommentCreateRequestDto {

    private String writerId;

    private String content;

}
