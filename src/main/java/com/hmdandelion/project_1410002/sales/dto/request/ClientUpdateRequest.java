package com.hmdandelion.project_1410002.sales.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateRequest {
    @NotNull
    @NotBlank
    private String clientName;
    @NotNull
    @NotBlank
    private String address;
    private String addressDetail;
    @NotNull
    @NotBlank
    private String postcode;
    @NotNull
    @NotBlank
    private String representativeName;
    @NotNull
    @NotBlank
    private String phone;
}
