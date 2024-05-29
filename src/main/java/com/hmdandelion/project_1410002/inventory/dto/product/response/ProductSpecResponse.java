package com.hmdandelion.project_1410002.inventory.dto.product.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.ProductSpec;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class ProductSpecResponse {
    private String color;
    private String type;
    private String size;
    private Long productCode;
    private String productName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime launchDate;
    private Long price;
    private String unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private ProductStatus status;

    public ProductSpecResponse(String color, String type, String size) {
        this.color = color;
        this.type = type;
        this.size = size;
    }

    public static ProductSpecResponse from(ProductSpec productSpec) {
        return new ProductSpecResponse(
                productSpec.getColor(),
                productSpec.getType(),
                productSpec.getSize()
        );
    }

    public static ProductSpecResponse of(String color, String type, String size, Long productCode, String productName, LocalDateTime launchDate, Long price, String unit, LocalDateTime updatedAt, ProductStatus status) {
        return new ProductSpecResponse(
                color,
                type,
                size,
                productCode,
                productName,
                launchDate,
                price,
                unit,
                updatedAt,
                status
        );
    }

//    public static ProductSpecResponse of(String color, String type, String size, Product product) {
//        return new ProductSpecResponse(
//                color,
//                type,
//                size,
//                product
//        );
//    }
}
