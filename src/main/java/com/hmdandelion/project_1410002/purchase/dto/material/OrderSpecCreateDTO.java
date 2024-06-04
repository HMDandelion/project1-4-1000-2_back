package com.hmdandelion.project_1410002.purchase.dto.material;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderSpecCreateDTO {
    private final Long specCode;
    private final int price;
    private final int orderQuantity;

}
