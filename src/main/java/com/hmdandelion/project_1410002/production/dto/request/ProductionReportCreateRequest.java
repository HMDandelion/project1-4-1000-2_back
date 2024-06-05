package com.hmdandelion.project_1410002.production.dto.request;

import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionReportCreateRequest {


    // ProductionManagement fields
    private final LocalDateTime startAt;
    private final LocalDateTime completedAt;
    private final Integer totalProductionQuantity;
    private final String productionFile;
    private final ProductionStatusType productionStatus;
    private final InspectionStatusType inspectionStatus;


    // ProductionDetail fields
    private final Long productionDetailCode;
    private final LocalDateTime inspectionDate;
    private final Integer productionQuantity;
    private final Integer defectQuantity;
    private final Integer completelyQuantity;
    private final String productionMemo;
    private final ProductionStatusType productionStatusType;

    // defectDetail
    private final Long defectCode;
    private final String defectReason;
    private final DefectStatusType defectStatus;
    private final String defectFile;

}