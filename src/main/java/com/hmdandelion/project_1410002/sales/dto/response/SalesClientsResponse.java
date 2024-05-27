package com.hmdandelion.project_1410002.sales.dto.response;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesClientsResponse {
    private final Long clientCode;
    private final String clientName;
    private final String representativeName;
    private final String phone;

    public static SalesClientsResponse from(final Client client) {
        return new SalesClientsResponse(
                client.getClientCode(),
                client.getClientName(),
                client.getRepresentativeName(),
                client.getPhone()
        );
    }
}
