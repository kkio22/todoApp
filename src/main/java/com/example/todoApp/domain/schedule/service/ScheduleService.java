package com.example.todoApp.domain.schedule.service;

import com.example.todoApp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleUpdateRequestDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleCreateResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleUpdateResponseDto;
import com.example.todoApp.domain.schedule.entity.Schedule;
import com.example.todoApp.domain.schedule.exception.ScheduleErrorCode;
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


    public ScheduleCreateResponseDto createSchedule(ScheduleCreateRequestDto scheduleCreateRequestDto) {

        Schedule schedule = new Schedule(
                scheduleCreateRequestDto.getUserId(),
                scheduleCreateRequestDto.getTitle(),
                scheduleCreateRequestDto.getContent()
        );// dto -> entity 변환

        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(
                saveSchedule.getUserId(),
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getCreatedAt()
        );
    }


    public List<ScheduleListResponseDto> findScheduleByPage(Long page, Long size) {

        // DB에서 page할 수 있게 설정함
        Pageable pageable = PageRequest.of(page.intValue() - 1, size.intValue(), Sort.by("id").descending());

        //entity에서 꺼내기
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);// DB 전체 조회 -> 페이징 단위로 끊어서 pageable 덩어리 객체로 전달

        List<Schedule> scheduleList = new ArrayList<>(schedulePage.getContent()); //scheduleList에 페이징 단위의 객체에 있는 일정을 나눠서 하나하나 넣음

        return scheduleList.stream()//List를 stream으로 바뀜 => map으로 나타내려고
                .map(schedule -> new ScheduleListResponseDto(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getCreatedAt()
                ))//entity -> dto로 변환
                .toList();//다시 List로 변환
    }

    public ScheduleResponseDto findById(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {

        Schedule findSchedule = verify(scheduleId, scheduleRequestDto);

        return new ScheduleResponseDto(
                findSchedule.getUserId(),
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getCreatedAt()
        );
    }

    @Transactional
    public ScheduleUpdateResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        Schedule findSchedule = check(scheduleId, scheduleUpdateRequestDto);

        findSchedule.update(
                scheduleUpdateRequestDto.getTitle(),
                scheduleUpdateRequestDto.getContent()
        );

        return new ScheduleUpdateResponseDto(
                findSchedule.getUserId(),
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getUpdatedAt()
        );
    }


    public void deleteSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {

        Schedule findSchedule = verify(scheduleId, scheduleRequestDto);

        scheduleRepository.delete(findSchedule); // scheduleId로 연결된 댓글도 삭제
    }


    private Schedule verify(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));

        if (findSchedule.getUserId() != scheduleRequestDto.getUserId()) {
            throw new CustomException(ScheduleErrorCode.NOT_FOUND_USER);
        }

        return findSchedule;
    }

    private Schedule check(Long scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));

        if (findSchedule.getUserId() != scheduleUpdateRequestDto.getUserId()) {
            throw new CustomException(ScheduleErrorCode.NOT_FOUND_USER);
        }

        return findSchedule;

    }
}





