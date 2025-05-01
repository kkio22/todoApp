package com.example.todoApp.domain.comment.entity;

import com.example.todoApp.api.BaseEntity;
import com.example.todoApp.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private boolean is_deleted;

    @ManyToOne // FK
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(){

    }

}
