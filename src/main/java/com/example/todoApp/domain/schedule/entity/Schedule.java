package com.example.todoApp.domain.schedule.entity;

import com.example.todoApp.api.BaseEntity;
import com.example.todoApp.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column//nullable = ture 자동으로 생성
    private LocalDateTime deletedAt;

    @Column
    private boolean is_deleted;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>(); // 일정 <-> 댓글 양방향 연결

    public Schedule() {

    }

    public Schedule(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
