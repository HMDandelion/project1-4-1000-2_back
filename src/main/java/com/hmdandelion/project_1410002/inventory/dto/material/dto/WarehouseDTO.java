package com.hmdandelion.project_1410002.inventory.dto.material.dto;

import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WarehouseDTO {

    private final String warehouseName;
    private final String location;
    private final long volume;

    public static WarehouseDTO from(Warehouse warehouse) {
        return new WarehouseDTO(
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume()
        );
    }
}
