package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateRequest {

    private String clientName;
    private String address;
    private String addressDetail;
    private String postcode;
    private String representativeName;
    private String phone;
}
