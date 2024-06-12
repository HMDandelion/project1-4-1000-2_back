package com.hmdandelion.project_1410002.inventory.dto.product.dto;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class BomDTO {
    private final String materialName;
    private final String categoryName;
    private final Long quantity;

    public static BomDTO from(Bom bom) {
        return new BomDTO(
                bom.getMaterialSpec().getMaterialName(),
                bom.getMaterialSpec().getCategory().getCategoryName(),
                bom.getQuantity()
        );
    }
}
