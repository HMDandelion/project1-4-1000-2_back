package com.hmdandelion.project_1410002.inventory.dto.warehouse.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WarehouseUpdateRequest {
    private String name;
    private String location;
    private Long volume;
    private Long employeeCode;
}
