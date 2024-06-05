package com.hmdandelion.project_1410002.purchase.dto.material.response;

import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import lombok.Getter;

@Getter
public class MaterialClientDetailResponse extends MaterialClientDTO {

    private final String address;
    private final String addressDetail;
    private final String postcode;
    private final String phone;

    public MaterialClientDetailResponse(Client client) {
        super(client);
        this.address = client.getAddress();
        this.addressDetail = client.getAddressDetail();
        this.postcode = client.getPostcode();
        this.phone = client.getPhone();
    }

    public static MaterialClientDetailResponse from(final Client client) {
        return new MaterialClientDetailResponse(client);
    }
}
