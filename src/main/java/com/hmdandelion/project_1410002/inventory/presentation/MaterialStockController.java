package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.dto.response.material.MaterialObjectListRes;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialStockRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialGraphRes;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialStockRes;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialTransactionsRes;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import com.hmdandelion.project_1410002.inventory.service.MaterialStockService;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    //재고목록 조회
    @GetMapping("/inventory")
    public ResponseEntity<MaterialStockRes> searchMaterialStock(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(required = false) final String materialName,
            @RequestParam(required = false) final Long warehouseCode,
            @RequestParam(required = false) final Long specCategoryCode
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        final List<MaterialStockSimpleDTO> list = materialStockService.searchMaterialStock(pageable, materialName, warehouseCode, specCategoryCode);
        MaterialStockRes res = MaterialStockRes.from(list);
        return ResponseEntity.ok(res);
    }

    //재고 상세 조회
    @GetMapping("/inventory/{stockCode}")
    public ResponseEntity<MaterialStockRes> findByIdForDetail(
            @PathVariable final Long stockCode
    ) {
        MaterialStockDetailDTO found = materialStockService.findById(stockCode);
        MaterialStockRes res = MaterialStockRes.from(found);
        return ResponseEntity.ok(res);
    }

    //재고 등록/수정
    @PostMapping("/inventory")
    public ResponseEntity<Void> saveStock(
            @RequestBody final SaveMaterialStockRequest request
    ) {
        final Long stockCode = materialStockService.save(request);

        return ResponseEntity.created(URI.create("/api/v1/material/inventory/" + stockCode)).build();
    }
    //재고 삭제
    @DeleteMapping("/inventory/{stockCode}")
    public ResponseEntity<Void> deleteStock(
        @PathVariable final Long stockCode
    ) {
        materialStockService.delete(stockCode);

        return ResponseEntity.noContent().build();
    }
}
