package com.hmdandelion.project_1410002.inventory.dto.product.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BomCreateRequest {
    private Long quantity;
    private Long sequence;
    private Long specCode;
}
