package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class StorageFilterResponse {
    private Long storageCode;
    private String productName;
    private Long actualQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    private Long destroyQuantity;
    private String warehouseName;
    private Long storageDate;
    private Boolean isToday;

    public StorageFilterResponse(Storage storageEntity) {
        this.storageCode = storageEntity.getStorageCode();
        this.productName = storageEntity.getStock().getProduct().getProductName();
        this.actualQuantity = storageEntity.getActualQuantity();
        this.createdDate = storageEntity.getCreatedAt();
        this.destroyQuantity = storageEntity.getDestroyQuantity();
        this.warehouseName = storageEntity.getWarehouse().getName();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(storageEntity.getCreatedAt(), now);
        this.storageDate = duration.toDays();
        this.isToday = now.toLocalDate().equals(storageEntity.getCreatedAt().toLocalDate());
    }
}
