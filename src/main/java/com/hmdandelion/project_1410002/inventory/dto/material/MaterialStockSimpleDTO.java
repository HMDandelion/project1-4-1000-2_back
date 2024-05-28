package com.hmdandelion.project_1410002.inventory.dto.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.Format;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialStockSimpleDTO {
    private final Long stockCode;
    private final String materialName;
    private final String categoryName;
    private final String warehouseName;
    private final int actualQuantity;
    private final String unit;
    private final String storageDate;
    private final String specification;

    public static MaterialStockSimpleDTO from(MaterialStock stock) {
        return new MaterialStockSimpleDTO(
                stock.getStockCode(),
                stock.getMaterialSpec().getMaterialName(),
                stock.getMaterialSpec().getCategory().getCategoryName(),
                stock.getWarehouse().getName(),
                stock.getActualQuantity(),
                stock.getMaterialSpec().getUnit(),
                stock.getStorageDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getMaterialSpec().getSpecification()
        );
    }
}
