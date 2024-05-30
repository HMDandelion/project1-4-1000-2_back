package com.hmdandelion.project_1410002.inventory.dto.material.dto;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MaterialStockSimpleDTO extends MaterialStockDTO {
    protected final String materialName;
    protected final String categoryName;
    protected final String warehouseName;
    protected final String specification;

    protected MaterialStockSimpleDTO(Long stockCode, int actualQuantity, String unit, String storageDate, String materialName, String categoryName, String warehouseName, String specification) {
        super(stockCode, actualQuantity, unit, storageDate);
        this.materialName = materialName;
        this.categoryName = categoryName;
        this.warehouseName = warehouseName;
        this.specification = specification;
    }

    public static MaterialStockSimpleDTO from(MaterialStock stock) {
        return new MaterialStockSimpleDTO(
                stock.getStockCode(),
                stock.getActualQuantity(),
                stock.getMaterialSpec().getUnit(),
                stock.getStorageDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getMaterialSpec().getMaterialName(),
                stock.getMaterialSpec().getCategory().getCategoryName(),
                stock.getWarehouse().getName(),
                stock.getMaterialSpec().getSpecification()
        );
    }
}
