package com.example.todoApp.domain.schedule.service;

import com.example.todoApp.domain.comment.dto.response.CommentListResponseDto;
import com.example.todoApp.domain.comment.entity.Comment;
import com.example.todoApp.domain.comment.service.CommentService;
import com.example.todoApp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleUpdateRequestDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleCreateResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleUpdateResponseDto;
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


@Service
@RequiredArgsConstructor
@Getter
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final CommentService commentService;

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
                saveSchedule.getCreatedAt()
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

        return scheduleList.stream()//List를 stream으로 바뀜 => map으로 나타내려고
                .map(schedule -> new ScheduleListResponseDto(
                        schedule.getId(),
                        schedule.getWriterId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getCreatedAt()
                ))//entity -> dto로 변환
                .toList();//다시 List로 변환
    }

    /*
    일정 단건 조회
     */

    public ScheduleResponseDto findById(Long scheduleId) {

        Schedule findSchedule = scheduleRepository.findByIdWithComment(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));//여기서 schedule entity 가져오는거고

        List<Comment> commentList = findSchedule.getComments();//매핑된 댓글를 List로 덩어리째 가져옴

        List<CommentListResponseDto> commentListResponseDto = commentList.stream()
                .map(comment -> new CommentListResponseDto(
                        comment.getId(),
                        comment.getWriterId(),
                        comment.getContent(),
                        comment.getUpdatedAt()
                ))
                .toList();

        return new ScheduleResponseDto(
                findSchedule.getUserId(),
                findSchedule.getId(),
                findSchedule.getWriterId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                commentListResponseDto,
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





