package com.hmdandelion.project_1410002.inventory.dto.material.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.type.StockDivision;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SaveMaterialStockRequest {
    private Long stockCode;
    private String division;
    private Long specCode;
    private Long warehouseCode;
    private int incomingQuantity;
    private int actualQuantity;
    private String storageDatetime;
    private String remarks;
    private String inspectionDatetime;
    @LastModifiedDate
    private LocalDateTime modificationDatetime;
    private String modificationReason;
    private int orderCode;
}
