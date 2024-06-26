package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrdersResponse {
    private final Long orderCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime orderDatetime;
    private final String clientName;
    private final OrderStatus status;
    private final LocalDate deadline;

    // private final List<String> productName;
}