package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageWarehouseDTO {
    String moveType;
    String productName;
    Long storageQuantity;
    String warehouseName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    public static StorageWarehouseDTO of(String moveType, String productName, Long initialQuantity, String name, LocalDateTime createdAt) {
        return new StorageWarehouseDTO(
                moveType,
                productName,
                initialQuantity,
                name,
                createdAt
        );
    }
}
