package com.hmdandelion.project_1410002.production.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionPlanCreateRequest {

    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String description;
}

