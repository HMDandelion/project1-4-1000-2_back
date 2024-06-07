package com.hmdandelion.project_1410002.inventory.dto.stock.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StorageDestroyRequest {
    private Long destroyQuantity;
}
