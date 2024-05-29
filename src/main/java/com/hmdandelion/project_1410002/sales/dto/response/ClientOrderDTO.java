package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientOrderDTO {

    private final Long orderCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime orderDatetime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deadline;
    private final Integer totalPrice;
    private final OrderStatus status;
}
