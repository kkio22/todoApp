package com.example.todoApp.domain.schedule.entity;

import com.example.todoApp.api.BaseEntity;
import com.example.todoApp.domain.childComment.entity.ChildComment;
import com.example.todoApp.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;

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
    private String writerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true) //부모가 삭제되면 자식도 삭제 & 부모와 연관관계가 끊기면 고아가 된 자식 객체들을 다 삭제하겠다 이 두가지 로직 다 실행
    private List<Comment> comments = new ArrayList<>(); // 일정 <-> 댓글 양방향 연결

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChildComment> childComments = new ArrayList<>();
    public Schedule() {

    }

    public Schedule(Long userId, String writerId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

    public void updateSchedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);

    }

}
