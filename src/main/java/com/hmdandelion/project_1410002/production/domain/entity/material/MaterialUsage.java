package com.hmdandelion.project_1410002.production.domain.entity.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tbl_material_usage")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usageCode;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private LocalDateTime usageDatetime;
    private Long employeeCode; //TODO 수정필
    //    private Long workOrderCode; //TODO 수정필
    @ManyToOne
    @JoinColumn(name = "work_order_code")
    private WorkOrder workOrder;
    @Enumerated(value = EnumType.STRING)
    private MaterialUsageStatus status;

    public MaterialUsage(WorkOrder workOrder, LocalDateTime usageDatetime) {
        this.workOrder = workOrder;
        this.usageDatetime = usageDatetime;
        this.status = MaterialUsageStatus.READY;
    }

    public static MaterialUsage of(WorkOrder workOrder) {
        return new MaterialUsage(
                workOrder, workOrder.getWorkOrderDate().atStartOfDay()
        );
    }
}
