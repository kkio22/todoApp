package com.example.todoApp.domain.schedule.repository;

import com.example.todoApp.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository// bean factory 등록
public interface ScheduleRepository extends JpaRepository <Schedule, Long> { //JpaRepository를 상속받은 레파지토리를 simpleRepository 인터페이스
}
