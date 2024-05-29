package com.hmdandelion.project_1410002.inventory.dto.material.dto;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MaterialStockDetailDTO extends MaterialStockDTO {
    protected final String inspectionDate;
    protected final String remarks;
    protected final MaterialSpecDTO spec;
    protected final WarehouseDTO warehouse;

    protected MaterialStockDetailDTO(Long stockCode, int actualQuantity, String unit, String storageDate, String inspectionDate, String remarks, MaterialSpec spec, Warehouse warehouse) {
        super(stockCode, actualQuantity, unit, storageDate);
        this.inspectionDate = inspectionDate;
        this.remarks = remarks;
        this.spec = MaterialSpecDTO.from(spec);
        this.warehouse = WarehouseDTO.from(warehouse);
    }

    public static MaterialStockDetailDTO from(MaterialStock stock) {
        return new MaterialStockDetailDTO(
                stock.getStockCode(),
                stock.getActualQuantity(),
                stock.getMaterialSpec().getUnit(),
                stock.getStorageDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getInspectionDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getRemarks(),
                stock.getMaterialSpec(),
                stock.getWarehouse()
        );
    }

}
