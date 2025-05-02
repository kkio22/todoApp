package com.example.todoApp.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentListResponseDto {

    private Long id;

    private String writerId;

    private String content;

    private LocalDateTime updatedAt;


}
