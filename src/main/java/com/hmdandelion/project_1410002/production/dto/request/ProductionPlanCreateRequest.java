package com.hmdandelion.project_1410002.production.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.PlannedOrderList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductionPlanCreateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endAt;

    private final List<ProductionPlannedListRequest> productionPlannedLists;

    private final List<PlannedOrderListRequest> plannedOrderListRequests;
}

