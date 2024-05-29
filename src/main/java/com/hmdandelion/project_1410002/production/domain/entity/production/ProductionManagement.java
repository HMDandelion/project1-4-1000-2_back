package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hmdandelion.project_1410002.Product.domian.entity.Product;
import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private int totalProductionQuantity;

    private String productionFile;


    @Enumerated(value = EnumType.STRING)
    private ProductionStatusType productionStatus = ProductionStatusType.REGISTER_PRODUCTION;

    @Enumerated(value = EnumType.STRING)
    private InspectionStatusType inspectionStatus = InspectionStatusType.BEFORE;

    @OneToMany(mappedBy = "productionManagement")
    private List<ProductionDetail> productionDetails;


}

