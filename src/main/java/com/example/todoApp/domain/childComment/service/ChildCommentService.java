package com.example.todoApp.domain.childComment.service;

import com.example.todoApp.domain.childComment.dto.request.ChildCommentCreateRequestDto;
import com.example.todoApp.domain.childComment.dto.request.ChildCommentUpdateRequestDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentCreateResponseDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentListResponseDto;
import com.example.todoApp.domain.childComment.dto.response.ChildCommentUpdateResponseDto;
import com.example.todoApp.domain.childComment.entity.ChildComment;
import com.example.todoApp.domain.childComment.repository.ChildCommentRepository;
import com.example.todoApp.domain.comment.entity.Comment;
import com.example.todoApp.domain.comment.repository.CommentRepository;
import com.example.todoApp.domain.schedule.entity.Schedule;
import com.example.todoApp.domain.schedule.repository.ScheduleRepository;
import com.example.todoApp.global.CustomException;
import com.example.todoApp.global.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildCommentService {

    private final ChildCommentRepository childCommentRepository;
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public ChildCommentCreateResponseDto createChildComment(Long commentId, ChildCommentCreateRequestDto commentCreateRequestDto) {

        Comment comment = verifyComment(commentId);

        Schedule schedule = verifySchedule(comment.getSchedule().getId());

        ChildComment childComment = new ChildComment(
                commentCreateRequestDto.getWriterId(),
                commentCreateRequestDto.getContent(),
                comment//이렇게 객체에 담겨도 db에는 id로 매핑되기때문에 id만 있는거지??
                 //이렇게 값을 다 넣어줘야 함 연관관계로 매핑되어있어도
        );

        comment.addChildComment(childComment); //양방향 매핑으로 comment에 childComment 들어감

        ChildComment saveComment = childCommentRepository.save(childComment);

        return new ChildCommentCreateResponseDto(
                saveComment.getId(),
                saveComment.getWriterId(),
                saveComment.getContent(),
                saveComment.getCreatedAt()
        );

    }

    public List<ChildCommentListResponseDto> findChildCommentByPage(Long commentId, Long page, Long size) {

        Comment comment = verifyComment(commentId);

        Schedule schedule = verifySchedule(comment.getSchedule().getId()); //이거에 대한 고민 튜터님께 질문!

        Pageable pageable = PageRequest.of(page.intValue() - 1, size.intValue(), Sort.by("id").descending());

        Page<ChildComment> childCommentPage = childCommentRepository.findAll(pageable);

        List<ChildComment> childCommentList = new ArrayList<>(childCommentPage.getContent());

        return childCommentList.stream()
                .map(childComment -> new ChildCommentListResponseDto(
                        childComment.getId(),
                        childComment.getWriterId(),
                        childComment.getContent(),
                        childComment.getUpdatedAt()
                )
                )
                .toList();
    }

    @Transactional
    public ChildCommentUpdateResponseDto updateChildComment(Long commentId, Long childCommentId, ChildCommentUpdateRequestDto childCommentUpdateRequestDto) {
        Comment comment = verifyComment(commentId);

        Schedule schedule = verifySchedule(comment.getSchedule().getId());

        ChildComment childComment = childCommentRepository.findById(childCommentId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CHILDCOMMENT));

        childComment.updateChildComment(
                childCommentUpdateRequestDto.getContent()
        );

        return new ChildCommentUpdateResponseDto(
                childComment.getId(),
                childComment.getWriterId(),
                childComment.getContent(),
                childComment.getUpdatedAt()
        );

    }

    public void deleteChildComment(Long commentId, Long childCommentId) {

        Comment comment = verifyComment(commentId);

        Schedule schedule = verifySchedule(comment.getSchedule().getId());

        ChildComment childComment = childCommentRepository.findById(childCommentId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CHILDCOMMENT));

        childCommentRepository.delete(childComment);

    }


    private Schedule verifySchedule(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        return findSchedule;
    }

    private Comment verifyComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
        return findComment;
    }


}
