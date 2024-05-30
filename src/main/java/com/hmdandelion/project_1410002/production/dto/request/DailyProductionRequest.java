package com.hmdandelion.project_1410002.production.dto.request;

import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DailyProductionRequest {

    private LocalDateTime startAt;
    private LocalDateTime completedAt;
    private int productionQuantity;
    private int defectQuantity;
    private int completelyQuantity;
    private String productionMemo;
    private int totalProductionQuantity;
    private String attachmentFile;
    private ProductionStatusType productionStatus;
    private InspectionStatusType inspectionStatus;
    private String productionFile;
    private String defectReason;
    private String defectStatus;
    private String defectFile;
    private LocalDateTime inspectionDate;

}
