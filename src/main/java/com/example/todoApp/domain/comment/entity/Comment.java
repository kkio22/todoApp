package com.example.todoApp.domain.comment.entity;

import com.example.todoApp.api.BaseEntity;
import com.example.todoApp.domain.comment.dto.request.CommentUpdateRequestDto;
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

    @Column
    private Long userId; //댓글을 작성하는 user table fk 나타냄

    @Column(nullable = false)
    private String writerId; //댓글 작성자 닉네임

    @Column(nullable = false)
    private String content;

    @Column
    private int commentCount = 0;

    @ManyToOne(fetch = FetchType.LAZY) // 이것때문에 n +1 발생
    private Schedule schedule;

    public Comment() {

    }

    public Comment(Long userId, String content, String writerId) {
        this.userId=userId;
        this.content=content;
        this.writerId=writerId;
        this.commentCount= ++commentCount;
    }

    public void updateComment(String content) {
        this.content=content;
    }
}
