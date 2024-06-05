package com.hmdandelion.project_1410002.purchase.dto.material.request;

import com.hmdandelion.project_1410002.sales.dto.request.ClientUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class MaterialClientModifyRequest extends ClientUpdateRequest {
    private List<Long> specCodes;

    public MaterialClientModifyRequest(String clientName,
                                       String address,
                                       String addressDetail,
                                       String postcode,
                                       String representativeName,
                                       String phone,
                                       List<Long> specCodes) {
        super(clientName, address, addressDetail, postcode, representativeName, phone);
        this.specCodes = specCodes;
    }
}
