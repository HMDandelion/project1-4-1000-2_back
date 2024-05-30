package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus.IN_PRODUCTION;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockProduct {

    private Long stockCode;
    private Long quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Boolean isDelete;
    private StockType type;
    private Long productCode;
    private String productName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime launchDate;

    private Long price;
    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private ProductStatus status;

    public StockProduct(Stock stock) {
        this.stockCode = stock.getStockCode();
        this.quantity = stock.getQuantity();
        this.createdAt = stock.getCreatedAt();
        this.isDelete = stock.getIsDelete();
        this.type = stock.getType();
        this.productCode = stock.getProduct().getProductCode();
        this.productName = stock.getProduct().getProductName();
        this.launchDate = stock.getProduct().getLaunchDate();
        this.price = stock.getProduct().getPrice();
        this.unit = stock.getProduct().getUnit();
        this.updatedAt = stock.getProduct().getUpdatedAt();
        this.status = stock.getProduct().getStatus();
    }


    public static StockProduct of(Long stockCode, Long quantity, LocalDateTime createdAt, Boolean isDelete, StockType type, Long productCode, String productName, LocalDateTime launchDate, Long price, String unit, LocalDateTime updatedAt, ProductStatus status) {
        return new StockProduct(
                stockCode,
                quantity,
                createdAt,
                isDelete,
                type,
                productCode,
                productName,
                launchDate,
                price,
                unit,
                updatedAt,
                status
        );
    }
}

