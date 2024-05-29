package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_production_detail")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_detail_code")
    private Long productionDetailCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_code")
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_status_code", insertable = false, updatable = false)
    private ProductionManagement productionManagement;


    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    private int productionQuantity;

    @Column(name = "defect_quantity")
    private Integer defectQuantity;

    @Column(name = "completely_quantity")
    private Integer completelyQuantity;

    @Column(name = "memo")
    private String productionMemo;

}