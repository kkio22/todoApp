package com.example.todoApp.domain.schedule.controller;

import com.example.todoApp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleRequestDto;
import com.example.todoApp.domain.schedule.dto.request.ScheduleUpdateRequestDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleCreateResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.todoApp.domain.schedule.dto.response.ScheduleUpdateResponseDto;
import com.example.todoApp.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor //초기화 되지않은 final 필드나, @NotNull이 붙은 필드에 대해 생성자 생성
@RequestMapping("/schedules") // 선언하는게 restful한 명시에 핵심
public class ScheduleController {

    private final ScheduleService scheduleService;

    /*
     게시글 생성
     */
    @PostMapping
    public ResponseEntity<ScheduleCreateResponseDto> createSchedule(
            @RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto
    ) {
        ScheduleCreateResponseDto scheduleCreateResponseDto = scheduleService.createSchedule(scheduleCreateRequestDto);

        return new ResponseEntity<>(scheduleCreateResponseDto, HttpStatus.CREATED);

    }


    @GetMapping
    public ResponseEntity<List<ScheduleListResponseDto>> findScheduleByPage(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long size
            ) {
        List<ScheduleListResponseDto> scheduleListResponseDto = scheduleService.findScheduleByPage(page, size);

        return new ResponseEntity<>(scheduleListResponseDto, HttpStatus.OK);

    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findById(
            @PathVariable Long scheduleId
            ){
       ScheduleResponseDto scheduleResponseDto = scheduleService.findById(scheduleId);

       return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto
    ){
      ScheduleUpdateResponseDto scheduleUpdateResponseDto = scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto);

      return new ResponseEntity<>(scheduleUpdateResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule (
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ){
        scheduleService.deleteSchedule(scheduleId, scheduleRequestDto);
        return new ResponseEntity<>("일정이 삭제되었습니다", HttpStatus.OK);
    }



}
