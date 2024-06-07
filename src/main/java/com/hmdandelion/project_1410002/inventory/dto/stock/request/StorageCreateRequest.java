package com.hmdandelion.project_1410002.inventory.dto.stock.request;

import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class StorageCreateRequest {
    private Long initialQuantity;
    private Long warehouseCode;
    private Long actualQuantity;
}
