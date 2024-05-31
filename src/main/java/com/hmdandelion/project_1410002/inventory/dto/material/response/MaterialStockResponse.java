package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.WarehouseDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialStockResponse {

    private final Long stockCode;
    private final int actualQuantity;
    private final String unit;
    private final String storageDate;
    private final String inspectionDate;
    private final String remarks;
    private final MaterialSpecDTO spec;
    private final WarehouseDTO warehouse;


    public static MaterialStockResponse from(MaterialStock stock) {
        return new MaterialStockResponse(
                stock.getStockCode(),
                stock.getActualQuantity(),
                stock.getMaterialSpec().getUnit(),
                stock.getStorageDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getInspectionDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                stock.getRemarks(),
                MaterialSpecDTO.from(stock.getMaterialSpec()),
                WarehouseDTO.from(stock.getWarehouse())
        );
    }

}
