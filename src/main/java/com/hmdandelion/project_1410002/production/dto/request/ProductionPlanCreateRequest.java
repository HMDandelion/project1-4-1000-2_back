package com.hmdandelion.project_1410002.production.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionPlanCreateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime endAt;
    private final String description;
}

