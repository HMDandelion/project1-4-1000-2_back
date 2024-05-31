package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class EstimateCreateRequest {
    private final LocalDate deadline;
    @Setter
    private Long clientCode;
    private final ClientCreateRequest client;
    private final List<EstimateProductRequest> products;
}
