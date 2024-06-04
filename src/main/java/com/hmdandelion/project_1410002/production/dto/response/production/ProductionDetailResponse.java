package com.hmdandelion.project_1410002.production.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionDetailResponse {
    private final Long productionDetailCode;
    private final Long workOrderCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime inspectionDate;
    private final Integer productionQuantity;
    private final Integer defectQuantity;
    private final Integer completelyQuantity;
    private final String productionMemo;

    public static ProductionDetailResponse from(ProductionDetail productionDetail) {
        return new ProductionDetailResponse(
                productionDetail.getProductionDetailCode(),
                productionDetail.getWorkOrder().getWorkOrderCode(),
                productionDetail.getInspectionDate(),
                productionDetail.getProductionQuantity(),
                productionDetail.getDefectQuantity(),
                productionDetail.getCompletelyQuantity(),
                productionDetail.getProductionMemo()
        );
    }
}
