package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "production_status_code")
    private ProductionManagement productionManagement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_code")
    private WorkOrder workOrder;

    @Column(name = "production_quantity")
    private Integer productionQuantity;

    @Column(name = "defect_quantity")
    private Integer defectQuantity;

    @Column(name = "completely_quantity")
    private Integer completelyQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Enumerated(value = EnumType.STRING)
    private InspectionStatusType inspectionStatus = InspectionStatusType.BEFORE;

    private String productionMemo;

    @Enumerated(value = EnumType.STRING)
    private ProductionStatusType productionStatus = ProductionStatusType.REGISTER_PRODUCTION;

    @OneToMany(mappedBy = "productionDetail")
    private List<DefectDetail> defectDetails;

    public ProductionDetail(ProductionManagement newProductionManagement, WorkOrder workOrder, Integer productionQuantity, Integer defectQuantity, Integer completelyQuantity, LocalDateTime inspectionDate, InspectionStatusType inspectionStatusType, String productionMemo, ProductionStatusType productionStatus) {

        this.productionManagement = newProductionManagement;
        this.workOrder = workOrder;
        this.productionQuantity = productionQuantity;
        this.defectQuantity = defectQuantity;
        this.completelyQuantity = completelyQuantity;
        this.inspectionDate = inspectionDate;
        this.inspectionStatus = inspectionStatusType;
        this.productionMemo = productionMemo;
        this.productionStatus = productionStatus;

    }


    public static ProductionDetail of(ProductionManagement newProductionManagement, WorkOrder workOrder, Integer productionQuantity, Integer defectQuantity,
                                      Integer completelyQuantity, LocalDateTime inspectionDate, InspectionStatusType inspectionStatusType,
                                      String productionMemo, ProductionStatusType productionStatus) {

        return new ProductionDetail(
                newProductionManagement,
                workOrder,
                productionQuantity,
                defectQuantity,
                completelyQuantity,
                inspectionDate,
                inspectionStatusType,
                productionMemo,
                productionStatus
        );
    }



    public void modifyDetail( int productionQuantity, int defectQuantity,
                             int completelyQuantity,LocalDateTime inspectionDate,InspectionStatusType inspectionStatusType,
                              String productionMemo, ProductionStatusType productionStatus
    ) {

        this.productionQuantity = productionQuantity;
        this.defectQuantity = defectQuantity;
        this.completelyQuantity = completelyQuantity;
        this.inspectionDate = inspectionDate;
        this.inspectionStatus = inspectionStatusType;
        this.productionMemo = productionMemo;
        this.productionStatus = productionStatus;
    }

    public void modify() {
        this.productionStatus = PRODUCTION_COMPLETED;
    }
}

