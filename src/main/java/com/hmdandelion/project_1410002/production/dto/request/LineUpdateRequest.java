package com.hmdandelion.project_1410002.production.dto.request;

import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LineUpdateRequest {

    @NotNull
    @NotBlank
    private String lineName;

    @NotNull
    private Integer lineProduction;

    @NotNull
    private LineStatusType lineStatusType;
}
