package com.hmdandelion.project_1410002.sales.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientCreateRequest {
    @NotNull
    @NotBlank
    private final String clientName;
    @NotNull
    @NotBlank
    private final String address;
    private final String addressDetail;
    @NotNull
    @NotBlank
    private final String postcode;
    @NotNull
    @NotBlank
    private final String representativeName;
    @NotNull
    @NotBlank
    private final String phone;

}
