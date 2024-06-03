package com.hmdandelion.project_1410002.inventory.dto.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BomResponse {
    private Long bomCode;
    private Long quantity;
    private Long sequence;
    private Long materialSpecCode;
}
