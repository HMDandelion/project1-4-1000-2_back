package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeftStockDTO {
    private Long initialQuantity;
    private Long assignmentQuantity;
    private Long leftQuantity;

    public static LeftStockDTO of(Long initialQuantity, Long assignmentQuantity, Long leftQuantity) {
        return new LeftStockDTO(
                initialQuantity,
                assignmentQuantity,
                leftQuantity
        );
    }
}
