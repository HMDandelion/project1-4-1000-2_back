package com.hmdandelion.project_1410002.production.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionDetailResponse {
    private final Long productionDetailCode;
    private final Long workOrderCode;
    private final String lineName;
    private final String employeeName;
    private final String productName;
    private final Integer orderedQuantity;
    private final Integer productionQuantity;
    private final Integer defectQuantity;
    private final Integer completelyQuantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime inspectionDate;
    private final InspectionStatusType inspectionStatusType;
    private final String productionMemo;
    private final ProductionStatusType productionStatusType;


    public static ProductionDetailResponse of(Long productionDetailCode, Long workOrderCode, String lineName,
                                              String employeeName, String productName, Integer orderedQuantity,
                                              Integer productionQuantity, Integer defectQuantity, Integer completelyQuantity,
                                              LocalDateTime inspectionDate, InspectionStatusType inspectionStatusType, String productionMemo,
                                              ProductionStatusType productionStatusType
    ) {
        return new ProductionDetailResponse(
                productionDetailCode,
                workOrderCode,
                lineName,
                employeeName,
                productName,
                orderedQuantity,
                productionQuantity,
                defectQuantity,
                completelyQuantity,
                inspectionDate,
                inspectionStatusType,
                productionMemo,
                productionStatusType
        );
    }















//    public static ProductionDetailResponse from(ProductionDetail productionDetail) {
//        return new ProductionDetailResponse(
//                productionDetail.getProductionDetailCode(),
//                productionDetail.getWorkOrder().getWorkOrderCode(),
//                productionDetail.getProductionQuantity(),
//                productionDetail.getDefectQuantity(),
//                productionDetail.getCompletelyQuantity(),
//                productionDetail.getInspectionDate(),
//                productionDetail.getInspectionStatus(),
//                productionDetail.getProductionMemo()
//        );
//    }
}