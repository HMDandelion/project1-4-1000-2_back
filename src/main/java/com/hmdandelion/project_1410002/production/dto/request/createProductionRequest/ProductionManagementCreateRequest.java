package com.hmdandelion.project_1410002.production.dto.request.createProductionRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionManagementCreateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime completedAt;
    private final Integer totalProductionQuantity;
    private final String productionFile;
    private final ProductionStatusType productionStatus;

}











//public static ProductionManagementCreateRequest of(Long productionStatusCode, LocalDateTime startAt, LocalDateTime completedAt,
//                                                   String stylizationName, Integer  totalOrderedQuantity, Integer totalProductionQuantity, String productionFile,
//                                                   ProductionStatusType productionStatus) {
//    return new ProductionManagementCreateRequest(
//            productionStatusCode,
//            startAt,
//            completedAt,
//            stylizationName,
//            totalOrderedQuantity,
//            totalProductionQuantity,
//            productionFile,
//            productionStatus
//    );