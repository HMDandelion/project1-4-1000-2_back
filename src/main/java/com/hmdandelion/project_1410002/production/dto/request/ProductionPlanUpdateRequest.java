package com.hmdandelion.project_1410002.production.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ProductionPlanUpdateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endAt;

    private final String description;
}
