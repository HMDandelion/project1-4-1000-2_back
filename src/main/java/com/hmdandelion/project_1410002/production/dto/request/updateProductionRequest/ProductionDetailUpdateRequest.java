package com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest;

import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.request.createProductionRequest.DefectDetailCreateRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionDetailUpdateRequest {
    private final Long id;
    private final Long productionStatusCode;
    private final Long workOrderCode;
    private final Integer productionQuantity;
    private final Integer defectQuantity;
    private final Integer completelyQuantity;
    private final LocalDateTime inspectionDate;
    private final InspectionStatusType inspectionStatusType;
    private final String productionMemo;
    private final ProductionStatusType productionStatusType;
    private final DefectDetailUpdateRequest[] defectDetailUpdateRequest;
}