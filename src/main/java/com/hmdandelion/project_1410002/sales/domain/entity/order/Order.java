package com.hmdandelion.project_1410002.sales.domain.entity.order;

import com.hmdandelion.project_1410002.sales.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tbl_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCode;
    @CreatedDate
    private LocalDateTime orderDatetime;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDate deadline;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime completedAt;
    private Long clientCode;
    private Long estimateCode;
}