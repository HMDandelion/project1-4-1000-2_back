package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageStockWarehouseDTO {
    private Long initialQuantity;
    private Long destroyQuantity;
    private Long stockCode;
    private Long actualQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime storageStartAt;
    private Long quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime stockCreatedAt;
    private StockType type;
    private Long productCode;
    private AssignmentStatus assignmentStatus;
    private Long warehouseCode;
    private String name;
    private String location;
    private Long volume;


    public static StorageStockWarehouseDTO of(Long initialQuantity, Long destroyQuantity, Long storageCode, Long actualQuantity, LocalDateTime storageStartAt, Long quantity, LocalDateTime stockCreatedAt, StockType type, Long productCode, AssignmentStatus assignmentStatus, Long warehouseCode, String name, String location, Long volume) {
        return new StorageStockWarehouseDTO(
                initialQuantity,
                destroyQuantity,
                storageCode,
                actualQuantity,
                storageStartAt,
                quantity,
                stockCreatedAt,
                type,
                productCode,
                assignmentStatus,
                warehouseCode,
                name,
                location,
                volume
        );
    }
}
