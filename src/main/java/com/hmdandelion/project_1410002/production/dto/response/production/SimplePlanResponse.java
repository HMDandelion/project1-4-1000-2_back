package com.hmdandelion.project_1410002.production.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePlanResponse {

    private final Long planCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startAt;

    public static SimplePlanResponse from(ProductionPlan plan) {
        return new SimplePlanResponse(
                plan.getPlanCode(), plan.getStartAt()
        );
    }
}
