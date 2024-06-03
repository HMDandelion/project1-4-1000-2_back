package com.hmdandelion.project_1410002.sales.dto.response;

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
    private final LocalDateTime orderDatetime;
    private final String clientName;
    private final OrderStatus status;
    private final LocalDate deadline;
    // private final List<String> productName;
}