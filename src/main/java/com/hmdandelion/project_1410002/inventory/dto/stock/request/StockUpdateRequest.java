package com.hmdandelion.project_1410002.inventory.dto.stock.request;

import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StockUpdateRequest {
    private final StockType type;
    private final Long productCode;
}
