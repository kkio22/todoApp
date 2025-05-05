package com.example.todoApp.domain.schedule.dto.response;

import com.example.todoApp.domain.childComment.entity.ChildComment;
import com.example.todoApp.domain.comment.dto.response.CommentWithChildCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleWithCommentDto {

    private Long commentId;

    private String writerId;

    private String content;

    private LocalDateTime updateAt;

    private List<CommentWithChildCommentDto> CommentWithChildCommentDto;
}
