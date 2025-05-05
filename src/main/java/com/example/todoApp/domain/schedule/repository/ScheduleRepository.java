package com.example.todoApp.domain.schedule.repository;


import com.example.todoApp.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository// bean factory 등록
public interface ScheduleRepository extends JpaRepository <Schedule, Long> { //JpaRepository를 상속받은 레파지토리를 simpleRepository 인터페이스

    @Query("select s from Schedule s left join fetch s.comments c where s.id = :scheduleId order by c.createdAt asc")
    Schedule findByIdWithComment(@Param("scheduleId") Long scheduleId);//Param에 들어갈 건 where로 나타냄, 이건 그냥 comment 같이 가져오게만 하는 쿼리문이야


}
