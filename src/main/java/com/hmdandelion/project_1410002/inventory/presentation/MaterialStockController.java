package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.dto.response.material.MaterialObjectListRes;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialGraphRes;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialSpecRes;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialTransactionsRes;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import com.hmdandelion.project_1410002.inventory.service.MaterialStockService;
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
public class MaterialStockController {
    private final MaterialStockService materialStockService;
    private final MaterialOrderService materialOrderService;
    private final MaterialSpecService materialSpecService;

    //안전재고 대비 실재고량 조회
    @GetMapping("/safety-stock")
    public ResponseEntity<MaterialGraphRes> findSafetyStock() {
        final MaterialGraphRes res = MaterialGraphRes.from(materialStockService.findStocksBySpec());
        return ResponseEntity.ok(res);
    }

    //창고 별 적재현항 조회
    @GetMapping("/warehouses/{warehouseCode}")
    public ResponseEntity<MaterialObjectListRes> findStockByWarehouse(
            @PathVariable final long warehouseCode
    ) {
        final List<MaterialStockSimpleDTO> list = materialStockService.findBywarehouseCode(warehouseCode);
        final MaterialObjectListRes res = MaterialObjectListRes.from(Collections.singletonList(list));
        return ResponseEntity.ok(res);
    }

    //스펙 별 거래내역 조회
    @GetMapping("/transactions/{specCode}")
    public ResponseEntity<MaterialTransactionsRes> getTransactionsBySpecCode(
            @PathVariable final long specCode
    ) {
        Map<String, Double> monthTransactionMap = materialOrderService.getMonthTransactionsBySpecCode(specCode);
        List<MaterialOrderDTO> orders = materialOrderService.getLast10OrderBySpecCode(specCode);
        MaterialTransactionsRes res = MaterialTransactionsRes.of(monthTransactionMap, orders);

        return ResponseEntity.ok(res);
    }


}
