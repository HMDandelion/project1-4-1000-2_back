package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType.PRODUCTION_COMPLETED;

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

    //    private Long employeeCode;
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
    private String productionMemo;

    @Enumerated(value = EnumType.STRING)
    private ProductionStatusType productionStatus = ProductionStatusType.REGISTER_PRODUCTION;

    public ProductionDetail(ProductionManagement newProductionManagement, LocalDateTime inspectionDate, int productionQuantity, int defectQuantity, int completelyQuantity, String productionMemo, ProductionStatusType productionStatus) {

        this.productionManagement = newProductionManagement;
        this.inspectionDate = inspectionDate;
        this.productionQuantity = productionQuantity;
        this.defectQuantity = defectQuantity;
        this.completelyQuantity = completelyQuantity;
        this.productionMemo = productionMemo;
        this.productionStatus = productionStatus;
    }


    public static ProductionDetail of(ProductionManagement newProductionManagement, LocalDateTime inspectionDate,
                                      int productionQuantity, int defectQuantity, int completelyQuantity,
                                      String productionMemo, ProductionStatusType productionStatus)
    {
        return new ProductionDetail(
                newProductionManagement,
                inspectionDate,
                productionQuantity,
                defectQuantity,
                completelyQuantity,
                productionMemo,
                productionStatus
        );
    }

    public void modifyDetail(LocalDateTime inspectionDate, int productionQuantity, int defectQuantity,
                             int completelyQuantity, String productionMemo, ProductionStatusType productionStatus
    ) {
        this.inspectionDate = inspectionDate;
        this.productionQuantity = productionQuantity;
        this.defectQuantity = defectQuantity;
        this.completelyQuantity = completelyQuantity;
        this.productionMemo = productionMemo;
        this.productionStatus = productionStatus;
    }

    public void modify() {
        this.productionStatus = PRODUCTION_COMPLETED;
    }
}

