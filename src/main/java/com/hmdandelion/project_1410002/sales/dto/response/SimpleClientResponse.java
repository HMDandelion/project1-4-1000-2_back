package com.hmdandelion.project_1410002.sales.dto.response;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimpleClientResponse {
    private final Long clientCode;
    private final String clientName;
    private final String representativeName;

    public static SimpleClientResponse from(Client client) {
        return new SimpleClientResponse(
                client.getClientCode(),
                client.getClientName(),
                client.getRepresentativeName()
        );
    }
}
