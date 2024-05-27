package com.hmdandelion.project_1410002.production.dto.response;

import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanListResponse {

    private final Long planCode;
    private final Long productCode;
    private final String productName;

//    public static PlanListResponse from(final ProductionPlan productionPlan) {
//        return new PlanListResponse(
//                productionPlan.getStartAt(),
//                productionPlan.getEndAt(),
//                productionPlan.getProduct().getProductCode(),
//                productionPlan.getProduct().getProductName()
//
//        );
//    }
}
