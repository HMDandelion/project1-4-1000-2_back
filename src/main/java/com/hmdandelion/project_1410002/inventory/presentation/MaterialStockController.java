package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockModifyRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialStockResponse;
import com.hmdandelion.project_1410002.inventory.service.MaterialStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialStockController {

    private final MaterialStockService materialStockService;


    //재고목록 조회
    @GetMapping("/inventory")
    public ResponseEntity<PagingResponse> searchMaterialStock(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(required = false) final String materialName,
            @RequestParam(required = false) final Long warehouseCode,
            @RequestParam(required = false) final Long specCategoryCode,
            @RequestParam(defaultValue = "10") final int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        final Page<MaterialStockSimpleDTO> list = materialStockService.searchMaterialStock(pageable, materialName, warehouseCode, specCategoryCode);
        PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(list);

        PagingResponse res = PagingResponse.of(list.getContent(), pagingButtonInfo);
        return ResponseEntity.ok(res);
    }

    //재고 상세 조회
    @GetMapping("/inventory/{stockCode}")
    public ResponseEntity<MaterialStockResponse> findByIdForDetail(
            @PathVariable final Long stockCode
    ) {
        MaterialStockResponse found = materialStockService.findById(stockCode);
        return ResponseEntity.ok(found);
    }

    //재고 등록
    @PostMapping("/inventory")
    public ResponseEntity<Void> createStock(
            @RequestBody final MaterialStockCreateRequest request
    ) {
        final Long stockCode = materialStockService.save(request);

        return ResponseEntity.created(URI.create("/api/v1/material/inventory/" + stockCode)).build();
    }

    //재고 수정
    @PutMapping("/inventory")
    public ResponseEntity<Void> modifyStock(
            @RequestBody final MaterialStockModifyRequest request
    ) {

        final Long stockCode = materialStockService.modify(request);

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