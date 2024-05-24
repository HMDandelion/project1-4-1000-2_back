package com.hmdandelion.project_1410002.production.domain.entity.production;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_production_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_detail_code")
    private Long productionDetailCode;

    @Column(name = "work_order_code")
    private Integer workOrderCode;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code")
    private Order order; */

    @Column(name = "production_status_code")
    private Long productionStatusCode;

    @Column(name = "employee_code")
    private Integer employeeCode;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "inspection_date")
    private LocalDate inspectionDate;

    @Column(name = "memo")
    private String memo;

    @Column(name = "defect_quantity")
    private Integer defectQuantity;

    @Column(name = "completely_quantity")
    private Integer completelyQuantity;

    @Column(name = "defect_reason")
    private String defectReason;

    @Column(name = "defect_status")
    private String defectStatus;

    @Column(name = "attachment_file")
    private String attachmentFile;

    // Constructors, getters, and setters
}