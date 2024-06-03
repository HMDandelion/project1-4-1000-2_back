package com.hmdandelion.project_1410002.inventory.dto.warehouse.response;

import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private Long warehouseCode;
    private String name;
    private String location;
    private Long volume;
    private Long employeeCode;

    public static WarehouseResponse from(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getWarehouseCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume(),
                warehouse.getEmployeeCode()
        );
    }

    public static WarehouseResponse of(Long warehouseCode, String name, String location, Long volume, Long employeeCode) {
        return new WarehouseResponse(
                warehouseCode,
                name,
                location,
                volume,
                employeeCode
        );
    }
}
