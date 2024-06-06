package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.ManageStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.domain.type.ReturnStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReturnsResponse {
    private final Long returnCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime returnDatetime;
    private final String clientName;
    private final Long orderCode;
    private final ManageType manageType;
    private final ManageStatus manageStatus;
    private final ReturnStatus returnStatus;
}
