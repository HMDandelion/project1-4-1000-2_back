package com.hmdandelion.project_1410002.production.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.product.domain.entity.Product;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanListResponse {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime endAt;
    private String requiredQuantity;
    private String plannedQuantity;
    private Long productCode;
    private String productName;

public PlanListResponse(ProductionPlannedList productionPlannedList
, Product product, ProductionPlan productionPlan) {
    this.startAt = productionPlan.getStartAt();
    this.endAt = productionPlan.getEndAt();
    this.requiredQuantity = productionPlannedList.getRequiredQuantity();
    this.plannedQuantity = productionPlannedList.getPlannedQuantity();
    this.productCode = product.getProductCode();
    this.productName = product.getProductName();
}
}
