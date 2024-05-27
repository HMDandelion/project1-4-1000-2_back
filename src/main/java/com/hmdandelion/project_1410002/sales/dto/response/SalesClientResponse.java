package com.hmdandelion.project_1410002.sales.dto.response;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SalesClientResponse {
    private final Long clientCode;
    private final String clientName;
    private final String address;
    private final String addressDetail;
    private final String postcode;
    private final String representativeName;
    private final String phone;
    private final List<ClientOrderDTO> orders;

    public static SalesClientResponse from(final Client client, final List<ClientOrderDTO> orders) {
        return new SalesClientResponse(
                client.getClientCode(),
                client.getClientName(),
                client.getAddress(),
                client.getAddressDetail(),
                client.getPostcode(),
                client.getRepresentativeName(),
                client.getPhone(),
                orders
        );
    }
}
