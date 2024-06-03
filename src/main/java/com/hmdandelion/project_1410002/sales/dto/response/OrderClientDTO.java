package com.hmdandelion.project_1410002.sales.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderClientDTO {
    private final Long clientCode;
    private final String clientName;
    private final String representativeName;
    private final String address;
    private final String addressDetail;
    private final String phone;
    // private final Integer latestOrderAmount;
}
