package com.example.todoApp.domain.comment.repository;

import com.example.todoApp.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

    Page<Comment> findAllByScheduleId(Long scheduleId, Pageable pageable);


    //List<Long> countAllByScheduleId(Long scheduleId);
}
