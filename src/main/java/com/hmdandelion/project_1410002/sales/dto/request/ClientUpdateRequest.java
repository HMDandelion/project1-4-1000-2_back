package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientUpdateRequest {

    private String clientName;
    private String address;
    private String addressDetail;
    private String postcode;
    private String representativeName;
    private String phone;
}
