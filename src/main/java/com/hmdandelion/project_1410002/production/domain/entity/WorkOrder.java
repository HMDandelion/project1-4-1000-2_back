package com.hmdandelion.project_1410002.production.domain.entity;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tbl_work_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_code")
    private Long workOrderCode;

    @CreatedDate
    @Column(name = "work_written_date", nullable = false)
    private LocalDate workWrittenDate;

    @Column(name = "ordered_quantity", nullable = false)
    private int orderedQuantity;

    @Enumerated(value = EnumType.STRING)
    private WorkOrderStatusType
            completionStatus = WorkOrderStatusType.IN_PROGRESS;

    @LastModifiedDate
    @Column(name = "work_modified_date", nullable = false)
    private LocalDateTime workModifiedDate;

    @Column(name = "work_order_date", nullable = false)
    private LocalDate workOrderDate;

    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "line_code", nullable = false)
    private Long lineCode;

    @Column(name = "employee_code", nullable = false)
    private Long employeeCode;

    public WorkOrder(LocalDate workWrittenDate, LocalDate workOrderDate, Long lineCode, Long productCode, Long employeeCode, Integer orderedQuantity, WorkOrderStatusType completionStatus) {
        this.workWrittenDate = workWrittenDate;
        this.workOrderDate = workOrderDate;
        this.lineCode = lineCode;
        this.productCode = productCode;
        this.employeeCode = employeeCode;
        this.orderedQuantity = orderedQuantity;
        this.completionStatus = completionStatus;
    }


    public static WorkOrder of(LocalDate workWrittenDate, LocalDate workOrderDate, Long lineCode, Long productCode, Long employeeCode, Integer orderedQuantity, WorkOrderStatusType completionStatus) {
        return new WorkOrder(
                workWrittenDate,
                workOrderDate,
                lineCode,
                productCode,
                employeeCode,
                orderedQuantity,
                completionStatus

        );
    }


    public void end() {
        if (this.completionStatus == WorkOrderStatusType.IN_PROGRESS) {
            this.completionStatus = WorkOrderStatusType.DONE;
        }
    }

    public void workOrderModify(LocalDate workOrderDate, Integer orderedQuantity, Long lineCode, Long employeeCode) {
        this.workOrderDate = workOrderDate;
        this.orderedQuantity = orderedQuantity;
        this.lineCode = lineCode;
        this.employeeCode = employeeCode;
    }
    public void setCompletionStatus(WorkOrderStatusType completionStatus) {
        this.completionStatus = completionStatus;
    }
}
