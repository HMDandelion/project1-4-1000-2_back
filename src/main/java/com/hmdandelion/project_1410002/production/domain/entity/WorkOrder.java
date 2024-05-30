package com.hmdandelion.project_1410002.production.domain.entity;

import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.sound.sampled.Line;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_work_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_code")
    private Long workOrderCode;

    @Column(name = "line_code")
    private Long lineCode;

    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "work_written_date", nullable = false)
    private LocalDateTime workWrittenDate;

    @Column(name = "ordered_quantity", nullable = false)
    private String orderedQuantity;

    @Column(name = "completion_status", nullable = false)
    private LocalDateTime completionStatus;
    @Enumerated(value = EnumType.STRING)
    private WorkOrderStatusType
            status = WorkOrderStatusType.IN_PROGRESS;

    @Column(name = "work_modified_date", nullable = false)
    private LocalDateTime workModifiedDate;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "line_code", nullable = false)
//    private Line line; <- line 엔티티 필요

    @Column(name = "work_order_date", nullable = false)
    private LocalDateTime workOrderDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_code", nullable = false)
//    private Product product; <- Product 엔티티 필요
}
