package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_production_management")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionStatusCode;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime completedAt;

    private Integer totalProductionQuantity;

    private String productionFile;

    @Enumerated(value = EnumType.STRING)
    private ProductionStatusType productionStatus = ProductionStatusType.REGISTER_PRODUCTION;



    @OneToMany(mappedBy = "productionManagement")
    private List<ProductionDetail> productionDetails;

    public ProductionManagement(LocalDateTime startAt, LocalDateTime completedAt, int totalProductionQuantity, String productionFile, ProductionStatusType productionStatus, InspectionStatusType inspectionStatus) {
        this.startAt = startAt;
        this.completedAt = completedAt;
        this.totalProductionQuantity = totalProductionQuantity;
        this.productionFile = productionFile;
    }


    public static ProductionManagement of(LocalDateTime startAt, LocalDateTime completedAt, int totalProductionQuantity, String productionFile, ProductionStatusType productionStatus, InspectionStatusType inspectionStatus) {
        return new ProductionManagement(
                startAt,
                completedAt,
                totalProductionQuantity,
                productionFile,
                productionStatus,
                inspectionStatus
        );
    }

    public void modifyReport(LocalDateTime startAt, LocalDateTime completedAt, int totalProductionQuantity,
                             String productionFile, ProductionStatusType productionStatus, InspectionStatusType inspectionStatus
    ) {
        this.startAt = startAt;
        this.completedAt = completedAt;
        this.totalProductionQuantity = totalProductionQuantity;
        this.productionFile = productionFile;
        this.productionStatus = productionStatus;
    }
}

