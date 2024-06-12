package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockProductDTO {

    private Long stockCode;
    private Long quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Boolean isDelete;
    private AssignmentStatus assignmentStatus;
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
    private Boolean isToday;


    public StockProductDTO(Stock stock) {
        this.stockCode = stock.getStockCode();
        this.quantity = stock.getQuantity();
        this.createdAt = stock.getCreatedAt();
        this.isDelete = stock.getIsDelete();
        this.assignmentStatus = stock.getAssignmentStatus();
        this.type = stock.getType();
        this.productCode = stock.getProduct().getProductCode();
        this.productName = stock.getProduct().getProductName();
        this.launchDate = stock.getProduct().getLaunchDate();
        this.price = stock.getProduct().getPrice();
        this.unit = stock.getProduct().getUnit();
        this.updatedAt = stock.getProduct().getUpdatedAt();
        this.status = stock.getProduct().getStatus();
        this.isToday = stock.getCreatedAt().toLocalDate().isEqual(LocalDate.now());
    }


    public static StockProductDTO of(Long stockCode, Long quantity, LocalDateTime createdAt, Boolean isDelete, AssignmentStatus assignmentStatus, StockType type, Long productCode, String productName, LocalDateTime launchDate, Long price, String unit, LocalDateTime updatedAt, ProductStatus status, Boolean isToday) {
        return new StockProductDTO(
                stockCode,
                quantity,
                createdAt,
                isDelete,
                assignmentStatus,
                type,
                productCode,
                productName,
                launchDate,
                price,
                unit,
                updatedAt,
                status,
                isToday
        );
    }

    public void setToday(boolean equal) {
        this.isToday = equal;
    }
}

