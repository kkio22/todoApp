package com.example.todoApp.domain.childComment.controller;


import com.example.todoApp.domain.childComment.dto.request.ChildCommentCreateRequestDto;
import com.example.todoApp.domain.childComment.dto.request.ChildCommentUpdateRequestDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentCreateResponseDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentListResponseDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentUpdateResponseDto;
import com.example.todoApp.domain.childComment.service.ChildCommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/{commentId}")
public class ChildCommentController {

    private final ChildCommentService childCommentService;

    /*
    대댓글 생성
     */
    @PostMapping("/childComments")//scheduleId를 comment에서 가져와서 사용하는게 더 좋은가??
    public ResponseEntity<ChildCommentCreateResponseDto> createChildComment(
            @PathVariable Long commentId,
            @RequestBody ChildCommentCreateRequestDto commentCreateRequestDto
    ) {
        ChildCommentCreateResponseDto childCommentCreateResponseDto = childCommentService.createChildComment(commentId, commentCreateRequestDto);

        return new ResponseEntity<>(childCommentCreateResponseDto, HttpStatus.CREATED);
    }

    /*
    대댓글 더보기 조회
     */
    @GetMapping("/childComments")
    public ResponseEntity<List<ChildCommentListResponseDto>> findChildComment(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long size
    ) {
        List<ChildCommentListResponseDto> childCommentListResponseDto = childCommentService.findChildCommentByPage(commentId, page, size);

        return new ResponseEntity<>(childCommentListResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/childComments/{childCommentId}")
    public ResponseEntity<ChildCommentUpdateResponseDto> updateChildComment(
            @PathVariable Long commentId,
            @PathVariable Long childCommentId,
            @RequestBody ChildCommentUpdateRequestDto childCommentUpdateRequestDto
    ) {
        ChildCommentUpdateResponseDto childCommentUpdateResponseDto = childCommentService.updateChildComment(commentId, childCommentId, childCommentUpdateRequestDto);

        return new ResponseEntity<>(childCommentUpdateResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/childComments/{childCommentId}")
    public ResponseEntity<String> deleteChildComment(
            @PathVariable Long commentId,
            @PathVariable Long childCommentId
    ) {
        childCommentService.deleteChildComment(commentId, childCommentId);

        return new ResponseEntity<>("대댓글이 삭제되었습니다.", HttpStatus.OK);
    }

}
