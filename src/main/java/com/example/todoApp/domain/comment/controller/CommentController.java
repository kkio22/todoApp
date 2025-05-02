package com.example.todoApp.domain.comment.controller;

import com.example.todoApp.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.todoApp.domain.comment.dto.request.CommentDeleteRequestDto;
import com.example.todoApp.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.todoApp.domain.comment.dto.response.CommentCreateResponseDto;
import com.example.todoApp.domain.comment.dto.response.CommentListResponseDto;
import com.example.todoApp.domain.comment.dto.response.CommentUpdateResponseDto;
import com.example.todoApp.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}")
public class CommentController {

    private final CommentService commentService;

    /*
    댓글 생성
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentCreateResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateRequestDto commentCreateRequestDto
    ) {
        CommentCreateResponseDto commentCreateResponseDto = commentService.createComment(scheduleId, commentCreateRequestDto);

        return new ResponseEntity<>(commentCreateResponseDto, HttpStatus.CREATED);
    }

    /*
    댓글 10개씩 페이징할 때 더보기 누를 때 사용
     */
    @GetMapping("/comments")
    public ResponseEntity<List<CommentListResponseDto>> findCommentByPage(
            @PathVariable Long scheduleId,
            @RequestParam Long page,
            @RequestParam Long size
    ) {
        List<CommentListResponseDto> commentListResponseDto = commentService.findCommentByPage(scheduleId, page, size);

        return new ResponseEntity<>(commentListResponseDto, HttpStatus.OK);
    }

    /*
    댓글 수정
     */
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ) {
        CommentUpdateResponseDto commentUpdateResponseDto = commentService.updateComment(scheduleId, commentId, commentUpdateRequestDto);

        return new ResponseEntity<>(commentUpdateResponseDto, HttpStatus.OK);
    }

    /*
    댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentDeleteRequestDto commentDeleteRequestDto
    ) {
        commentService.deleteComment(scheduleId, commentId, commentDeleteRequestDto);

        return new ResponseEntity<>("댓글을 삭제했습니다", HttpStatus.OK);
    }


}
