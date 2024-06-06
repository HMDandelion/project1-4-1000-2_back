package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.dto.response.material.MaterialObjectListResponse;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialGraphResponse;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialTransactionsResponse;
import com.hmdandelion.project_1410002.inventory.service.MaterialAnalyzeService;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialAnalyzeController {

    private final MaterialAnalyzeService materialAnalyzeService;
    private final MaterialOrderService materialOrderService;

    //안전재고 대비 실재고량 조회
    @GetMapping("/safety-stock")
    public ResponseEntity<MaterialGraphResponse> findSafetyStock() {
        final MaterialGraphResponse res = MaterialGraphResponse.from(materialAnalyzeService.findStocksBySpec());
        return ResponseEntity.ok(res);
    }

    //창고 별 적재현항 조회
    @GetMapping("/warehouses/{warehouseCode}")
    public ResponseEntity<MaterialObjectListResponse> findStockByWarehouse(
            @PathVariable final long warehouseCode
    ) {
        final List<MaterialStockSimpleDTO> list = materialAnalyzeService.findBywWrehouseCode(warehouseCode);
        final MaterialObjectListResponse res = MaterialObjectListResponse.from(Collections.singletonList(list));
        return ResponseEntity.ok(res);
    }

    //스펙 별 거래내역 조회
    @GetMapping("/transactions/{specCode}")
    public ResponseEntity<MaterialTransactionsResponse> getTransactionsBySpecCode(
            @PathVariable final long specCode
    ) {
        Map<String, Double> monthTransactionMap = materialOrderService.getMonthTransactionsBySpecCode(specCode);
        List<MaterialOrderDTO> orders = materialOrderService.getLast10OrderBySpecCode(specCode);
        MaterialTransactionsResponse res = MaterialTransactionsResponse.of(monthTransactionMap, orders);

        return ResponseEntity.ok(res);
    }
}
