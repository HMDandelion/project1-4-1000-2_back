package com.hmdandelion.project_1410002.purchase.dto.material.request;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.sales.dto.request.ClientCreateRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class MaterialClientCreateRequest extends ClientCreateRequest {
    List<Long> specCodes;

    public MaterialClientCreateRequest(String clientName,
                                       String address,
                                       String addressDetail,
                                       String postcode,
                                       String representativeName,
                                       String phone,
                                       List<Long> specCodes) {
        super(clientName,
              address,
              addressDetail,
              postcode,
              representativeName,
              phone);
        this.specCodes = specCodes;
    }
}
