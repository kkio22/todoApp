package com.example.todoApp.domain.childComment.repository;

import com.example.todoApp.domain.childComment.entity.ChildComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCommentRepository extends JpaRepository <ChildComment, Long> {
}
