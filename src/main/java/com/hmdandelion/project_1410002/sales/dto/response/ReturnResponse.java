package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.entity.returns.Return;
import com.hmdandelion.project_1410002.sales.domain.type.ManageStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.domain.type.ReturnStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReturnResponse {
    private final Long returnCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime returnDatetime;
    private final ManageType manageType;
    private final ManageStatus manageStatus;
    private final ReturnStatus returnStatus;
    private final String clientName;
    private final Long orderCode;
    private final ExchangeOrderResponse exchangeOrder;
    private final List<ReturnProductResponse> returnProducts;
}
