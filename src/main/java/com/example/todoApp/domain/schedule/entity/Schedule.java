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

    @Column(nullable = false, unique = true)
    private String writerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>(); // 일정 <-> 댓글 양방향 연결

    public Schedule() {

    }

    public Schedule(Long userId, String title, String content, String writerId) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
