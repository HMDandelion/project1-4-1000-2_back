package com.hmdandelion.project_1410002.sales.domain.entity.employee;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_position")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionCode;
    private String positionName;
    private int rank;
    private String status; //TODO 수정할 것
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}