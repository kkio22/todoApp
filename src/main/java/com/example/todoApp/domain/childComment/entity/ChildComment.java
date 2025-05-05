package com.example.todoApp.domain.childComment.entity;

import com.example.todoApp.api.BaseEntity;
import com.example.todoApp.domain.comment.entity.Comment;
import com.example.todoApp.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "childComment")
public class ChildComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String writerId;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)// 댓글 <-> 대댓글 양방향 매핑
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public ChildComment() {

    }

    public ChildComment(String writerId, String content, Comment comment) {
        this.writerId = writerId;
        this.content=content;
        this.comment=comment;
    }

    public void updateChildComment(String content) {
        this.content = content;
    }
}
