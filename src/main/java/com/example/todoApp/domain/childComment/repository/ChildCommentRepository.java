package com.example.todoApp.domain.childComment.repository;

import com.example.todoApp.domain.childComment.entity.ChildComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCommentRepository extends JpaRepository <ChildComment, Long> {

    Page<ChildComment> findAllByCommentId(Long commentId, Pageable pageable);
}
