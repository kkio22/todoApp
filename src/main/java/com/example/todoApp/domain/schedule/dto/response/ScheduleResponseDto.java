package com.example.todoApp.domain.schedule.dto.response;

import com.example.todoApp.domain.comment.dto.response.CommentListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter//프론트에 값 빼기위해
@AllArgsConstructor //생성자
public class ScheduleResponseDto {

    private Long userId;

    private Long id;

    private String writerId;

    private String title;

    private String content;

    private List<CommentListResponseDto> commentListResponseDto;

    private LocalDateTime updatedAt;

}
