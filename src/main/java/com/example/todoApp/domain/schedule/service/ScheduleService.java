package com.example.todoApp.domain.schedule.service;

import com.example.todoApp.domain.childComment.dto.response.ChildCommentCreateResponseDto;
import com.example.todoApp.domain.childComment.entity.ChildComment;
import com.example.todoApp.domain.comment.dto.response.CommentListResponseDto;
import com.example.todoApp.domain.comment.dto.response.CommentWithChildCommentDto;
import com.example.todoApp.domain.comment.entity.Comment;
import com.example.todoApp.domain.comment.repository.CommentRepository;
import com.example.todoApp.domain.comment.service.CommentService;
import com.example.todoApp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleUpdateRequestDto;
import com.example.todoApp.domain.schedule.dto.response.*;
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
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Getter
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;


    /*
    일정 생성
     */
    public ScheduleCreateResponseDto createSchedule(ScheduleCreateRequestDto scheduleCreateRequestDto) {

        Schedule schedule = new Schedule(
                scheduleCreateRequestDto.getUserId(),
                scheduleCreateRequestDto.getWriterId(),
                scheduleCreateRequestDto.getTitle(),
                scheduleCreateRequestDto.getContent()
        );// dto -> entity 변환

        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(
                saveSchedule.getUserId(),
                saveSchedule.getId(),
                saveSchedule.getWriterId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getUpdatedAt()
        );
    }

    /*
    일정 10개씩 조회
     */

    public List<ScheduleListResponseDto> findScheduleByPage(Long page, Long size) {

        // DB에서 page할 수 있게 설정함
        Pageable pageable = PageRequest.of(page.intValue() - 1, size.intValue(), Sort.by("id").descending());

        //entity에서 꺼내기
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);// DB 전체 조회 -> 페이징 단위로 끊어서 pageable 덩어리 객체로 전달

        List<Schedule> scheduleList = new ArrayList<>(schedulePage.getContent()); //scheduleList에 페이징 단위의 객체에 있는 일정을 나눠서 하나하나 넣음

       // List<Long> commentCount = commentRepository.countAllByScheduleId((scheduleList.stream().map(Schedule ::getId)).count());
                // index 방법 , 단뱡향일때는 사용할 수 있음
        return scheduleList.stream()//List를 stream으로 바뀜 => map으로 나타내려고
                .map(schedule -> new ScheduleListResponseDto( //schedule 객체 0부터 정해둔 끝까지 돌아다니면서 id, writerid, title, content, comments, updateAt을 0번째 꺼 new scheduleListResponseDto에 넣는 과정 반복
                        schedule.getId(),
                        schedule.getWriterId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getComments().size(),
                        schedule.getUpdatedAt()
                ))//entity -> dto로 변환
                .toList();//다시 List로 변환

    }

    /*
    일정 단건 조회 -> 댓글이 있는 없는 그냥 하나로 해결 가능, 없으면 빈 공간으로 내보냄
     */

    public ScheduleResponseDto findById(Long scheduleId) {

        Schedule findSchedule = scheduleRepository.findByIdWithComment(scheduleId);


        return new ScheduleResponseDto(
                findSchedule.getUserId(),
                findSchedule.getId(),
                findSchedule.getWriterId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getComments().stream().
                        sorted(Comparator.comparing(Comment :: getCreatedAt).reversed())
                        .map(comment -> new ScheduleWithCommentDto(
                                comment.getId(),
                                comment.getWriterId(),
                                comment.getContent(),
                                comment.getUpdatedAt(),
                                comment.getChildComment().stream().
                                        sorted(Comparator.comparing(ChildComment::getCreatedAt).reversed())
                                        .map(childComment -> new CommentWithChildCommentDto(
                                                childComment.getId(),
                                                childComment.getWriterId(),
                                                childComment.getContent(),
                                                childComment.getUpdatedAt()
                                        ))
                                        .toList()

                        ))
                        .toList(), //같이 가지고 나온 댓글을 dto 갹체 하나하나로 해서 다시 list로 반환
                findSchedule.getUpdatedAt()


        );

    }


    /*
    일정 수정
     */

    @Transactional
    public ScheduleUpdateResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (findSchedule.getUserId() != scheduleUpdateRequestDto.getUserId()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        findSchedule.updateSchedule(
                scheduleUpdateRequestDto.getTitle(),
                scheduleUpdateRequestDto.getContent()
        );

        return new ScheduleUpdateResponseDto(
                findSchedule.getUserId(),
                findSchedule.getId(),
                findSchedule.getWriterId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getUpdatedAt()
        );
    }

    /*
    일정 삭제
     */

    public void deleteSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (findSchedule.getUserId() != scheduleRequestDto.getUserId()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }


        scheduleRepository.delete(findSchedule); // scheduleId로 연결된 댓글도 삭제
    }
}





