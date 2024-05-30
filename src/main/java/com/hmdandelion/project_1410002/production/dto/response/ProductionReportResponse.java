package com.hmdandelion.project_1410002.production.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductionReportResponse {

    private Long productionStatusCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;
    private int totalProductionQuantity;
    private String productionFile;
    private String productionStatus;
    private String inspectionStatus;

    public static ProductionReportResponse from(ProductionManagement productionManagement) {
        return new ProductionReportResponse(
                productionManagement.getProductionStatusCode(),
                productionManagement.getStartAt(),
                productionManagement.getCompletedAt(),
                productionManagement.getTotalProductionQuantity(),
                productionManagement.getProductionFile(),
                productionManagement.getProductionStatus().toString(),
                productionManagement.getInspectionStatus().toString()
        );
    }
}