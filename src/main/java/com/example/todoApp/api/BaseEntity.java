package com.example.todoApp.api;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter// 필드가 private여서 필요함
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass //테이블 생성을 막아줌, 상속받은 entity에서 테이블 생성
public class BaseEntity {

    @CreatedDate//엔티티 객체가 처음 생성될 때 자동으로 시간을 설정해주는 annotation
    @Column(updatable = false) // 컬럼 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate//자동으로 db가 변경될 때 값이 저장됨
    private LocalDateTime updatedAt;

}
