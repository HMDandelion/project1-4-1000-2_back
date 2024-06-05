package com.hmdandelion.project_1410002.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_department")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentCode;
    private String departmentName;
    private String status; //TODO 저는 사용안할꺼라 스트링으로 해뒀습니다 by한결
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}