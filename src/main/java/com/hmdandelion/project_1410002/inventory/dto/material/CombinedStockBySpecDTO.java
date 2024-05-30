package com.hmdandelion.project_1410002.inventory.dto.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CombinedStockBySpecDTO {
    private final Long specCode;
    private final String materialName;
    private final String remarks;
    private final String unit;
    private final String categoryName;
    private final int safetyStock;
    private final String specification;
    private int actualQuantity;

    public void plusActualQuantity(int num) {
        this.actualQuantity += num;
    }

    public static CombinedStockBySpecDTO from(MaterialStock stock) {
        return new CombinedStockBySpecDTO(
                stock.getMaterialSpec().getSpecCode(),
                stock.getMaterialSpec().getMaterialName(),
                stock.getMaterialSpec().getRemarks(),
                stock.getMaterialSpec().getUnit(),
                stock.getMaterialSpec().getCategory().getCategoryName(),
                stock.getMaterialSpec().getSafetyStock(),
                stock.getMaterialSpec().getSpecification(),
                stock.getActualQuantity()
        );
    }
}
