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

    private int productionQuantity;

    @Column(name = "defect_quantity")
    private Integer defectQuantity;

    @Column(name = "completely_quantity")
    private Integer completelyQuantity;
    private String productionMemo;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Enumerated(value = EnumType.STRING)
    private InspectionStatusType inspectionStatus = InspectionStatusType.BEFORE;

    @Enumerated(value = EnumType.STRING)
    private ProductionStatusType productionStatus = ProductionStatusType.REGISTER_PRODUCTION;

    public ProductionDetail(ProductionManagement newProductionManagement, LocalDateTime inspectionDate, int productionQuantity, int defectQuantity, int completelyQuantity, String productionMemo, InspectionStatusType inspectionStatus, ProductionStatusType productionStatus) {

        this.productionManagement = newProductionManagement;
        this.productionQuantity = productionQuantity;
        this.defectQuantity = defectQuantity;
        this.completelyQuantity = completelyQuantity;
        this.inspectionDate = inspectionDate;
        this.inspectionStatus = inspectionStatus;
        this.productionMemo = productionMemo;
        this.productionStatus = productionStatus;
    }
}
//
//    public static ProductionDetail of(ProductionManagement newProductionManagement,
//                                      int productionQuantity, int defectQuantity, int completelyQuantity,
//                                      LocalDateTime inspectionDate,InspectionStatusType inspectionStatus,String productionMemo, ProductionStatusType productionStatus)
//    {
//        return new ProductionDetail(
//                newProductionManagement,
//                productionQuantity,
//                defectQuantity,
//                completelyQuantity,
//                inspectionDate,
//                inspectionStatus,
//                productionMemo,
//                productionStatus
//        );
//    }
//
//    public void modifyDetail(LocalDateTime inspectionDate, int productionQuantity, int defectQuantity,
//                             int completelyQuantity, String productionMemo, ProductionStatusType productionStatus
//    ) {
//
//        this.productionQuantity = productionQuantity;
//        this.defectQuantity = defectQuantity;
//        this.completelyQuantity = completelyQuantity;
//        this.inspectionDate = inspectionDate;
//        this.inspectionStatus = inspectionStatus;
//        this.productionMemo = productionMemo;
//        this.productionStatus = productionStatus;
//    }
//}

