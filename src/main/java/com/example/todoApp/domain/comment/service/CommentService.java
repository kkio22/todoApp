package com.example.todoApp.domain.comment.service;

import com.example.todoApp.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.todoApp.domain.comment.dto.request.CommentDeleteRequestDto;
import com.example.todoApp.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.todoApp.domain.comment.dto.response.CommentCreateResponseDto;
import com.example.todoApp.domain.comment.dto.response.CommentListResponseDto;
import com.example.todoApp.domain.comment.dto.response.CommentUpdateResponseDto;
import com.example.todoApp.domain.comment.entity.Comment;
import com.example.todoApp.domain.comment.repository.CommentRepository;
import com.example.todoApp.domain.schedule.entity.Schedule;
import com.example.todoApp.global.ErrorCode;
import com.example.todoApp.domain.schedule.repository.ScheduleRepository;
import com.example.todoApp.global.CustomException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Service
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public CommentCreateResponseDto createComment(Long scheduleId, CommentCreateRequestDto commentCreateRequestDto) {

        Schedule schedule = verifySchedule(scheduleId); // 댓글 달고 싶은 일정이 DB에 있는지 확인


        Comment comment = new Comment( // dto -> entity
                commentCreateRequestDto.getUserId(),
                commentCreateRequestDto.getContent(),
                commentCreateRequestDto.getWriterId(),
                schedule
        );


        schedule.addComment(comment);


        Comment saveComment = commentRepository.save(comment); //DB에 저장

        return new CommentCreateResponseDto( // entity -> dto
                saveComment.getUserId(), //댓글을 단 사용자가 누군지 원래는 연관관계가 mapping 되어야 함
                saveComment.getId(),
                saveComment.getWriterId(),
                saveComment.getContent(),
                saveComment.getCreatedAt()
        );
    }

    public List<CommentListResponseDto> findCommentByPage(Long scheduleId, Long page, Long size) {

        verifySchedule(scheduleId);

        Pageable pageable = PageRequest.of(page.intValue() - 1, size.intValue(), Sort.by("id").descending());

        Page<Comment> commentPage = commentRepository.findAllByScheduleId(scheduleId, pageable);

        List<Comment> commentList = new ArrayList<>(commentPage.getContent());

        for( Comment comment : commentList){
            if(! comment.getSchedule().getId().equals(scheduleId)){
                throw new CustomException(ErrorCode.SCHEDULE_ID_MISMATCH);
            }
        }



        return commentList.stream()
                .map(comment -> new CommentListResponseDto(
                                comment.getId(),
                                comment.getWriterId(),
                                comment.getContent(),
                                comment.getUpdatedAt()
                        )
                )
                .toList();
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long scheduleId, Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {

        verifySchedule(scheduleId);

        Comment findComment = verifyComment(commentId);

        if(! findComment.getSchedule().getId().equals(scheduleId)){
            throw new CustomException(ErrorCode.SCHEDULE_ID_MISMATCH);
        }

        if (! findComment.getUserId().equals(commentUpdateRequestDto.getUserId())) {
            throw new CustomException(ErrorCode.NOT_FOUND_OWNER);
        }

        findComment.updateComment(commentUpdateRequestDto.getContent());

        return new CommentUpdateResponseDto(
                findComment.getId(),
                findComment.getWriterId(),
                findComment.getContent(),
                findComment.getUpdatedAt()
        );

    }

    public void deleteComment(Long scheduleId, Long commentId, CommentDeleteRequestDto commentDeleteRequestDto) {

        verifySchedule(scheduleId);

        Comment findComment = verifyComment(commentId);

        if(! findComment.getSchedule().getId().equals(scheduleId)){
            throw new CustomException(ErrorCode.SCHEDULE_ID_MISMATCH);
        }

        if (! findComment.getUserId().equals(commentDeleteRequestDto.getUserId())) {
            throw new CustomException(ErrorCode.NOT_FOUND_OWNER);
        }

        commentRepository.delete(findComment); //댓글만 삭제 -> schedule은 삭제 X

    }

    private Comment verifyComment(Long commentId) {

        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

    }

    private Schedule verifySchedule(Long scheduleId) {

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
    }

}
