package com.hmdandelion.project_1410002.inventory.dto.warehouse.response;

import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
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
    private String employeeName;

    public static WarehouseResponse from(Warehouse warehouse, Employee employee) {
        return new WarehouseResponse(
                warehouse.getWarehouseCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume(),
                employee.getEmployeeName()
        );
    }

    public static WarehouseResponse of(Long warehouseCode, String name, String location, Long volume, String employeeName) {
        return new WarehouseResponse(
                warehouseCode,
                name,
                location,
                volume,
                employeeName
        );
    }
}
