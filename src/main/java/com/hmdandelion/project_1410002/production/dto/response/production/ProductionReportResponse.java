package com.hmdandelion.project_1410002.production.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductionReportResponse {

    private Long productionStatusCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;
    private Integer totalProductionQuantity;
    private String productionFile;
    private ProductionStatusType productionStatus;

    public static ProductionReportResponse from(final ProductionManagement productionManagement) {
        return new ProductionReportResponse(
                productionManagement.getProductionStatusCode(),
                productionManagement.getStartAt(),
                productionManagement.getCompletedAt(),
                productionManagement.getTotalProductionQuantity(),
                productionManagement.getProductionFile(),
                productionManagement.getProductionStatus()
        );
    }
}