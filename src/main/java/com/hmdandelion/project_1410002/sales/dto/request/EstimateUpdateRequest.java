package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class EstimateUpdateRequest {
    private final LocalDate deadline;
    private final List<EstimateProductRequest> products;
}
