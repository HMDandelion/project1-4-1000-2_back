package com.hmdandelion.project_1410002.sales.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class EstimateUpdateRequest {
    @NotNull
    private final LocalDate deadline;
    @NotNull
    private final List<EstimateProductRequest> products;
}
