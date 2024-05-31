package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientCreateRequest {

    private final String clientName;
    private final String address;
    private final String addressDetail;
    private final String postcode;
    private final String representativeName;
    private final String phone;

}
