package com.hmdandelion.project_1410002.inventory.dto.release.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseOrderLackDTO {
    private String productName;
    /*부족 수량*/
    private Long lackQuantity;
    /*부족 한 수량이 있는지 여부*/
    private Boolean isLack;

    public static ReleaseOrderLackDTO of(String productName, Long lackQuantity, Boolean isLack) {
        return new ReleaseOrderLackDTO(productName,
        lackQuantity,
        isLack
        );
    }
}
