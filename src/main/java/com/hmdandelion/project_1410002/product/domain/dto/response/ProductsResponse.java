package com.hmdandelion.project_1410002.product.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.product.domain.entity.Product;
import com.hmdandelion.project_1410002.product.domain.type.ProductStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Getter
@RequiredArgsConstructor
public class ProductsResponse {
    private final Long productCode;
    private final String productName;
    private final LocalDateTime launchDate;
    private final Long price;
    private final String unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime updated_at;
    private final ProductStatus status;

    public static ProductsResponse from(Product product) {
        return new ProductsResponse(
                product.getProductCode(),
                product.getProductName(),
                product.getLaunchDate(),
                product.getPrice(),
                product.getUnit(),
                product.getUpdated_at(),
                product.getStatus()
                );
    }

}
