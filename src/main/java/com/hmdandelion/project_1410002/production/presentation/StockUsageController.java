package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.dto.material.StockUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.request.StockUsageCreateRequest;
import com.hmdandelion.project_1410002.production.service.StockUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class StockUsageController {

    private final StockUsageService stockUsageService;

    //사용코드로 목록조회
    @GetMapping("/stock-usage/{usageCode}")
    public ResponseEntity<PagingResponse> getStockUsageList(
            @PathVariable final Long usageCode
    ) {
        List<StockUsageDTO> list = stockUsageService.findByUsageCode(usageCode);
        PagingResponse response = PagingResponse.of(list, null);
        return ResponseEntity.ok(response);
    }


    //전달목록으로 재고 등록
    @PostMapping("/stock-usage")
    public ResponseEntity<Void> createStockUsage(
            @RequestBody StockUsageCreateRequest request
    ) {
        stockUsageService.createStockUsage(request);
        return ResponseEntity.created(URI.create("api/v1/material/use/" + request.getUsageCode())).build();
    }

    //전달취소
    @DeleteMapping("/stock-usage")
    public ResponseEntity<Void> deleteStockUsage(
            @RequestParam final Long stockUsageCode
    ) {
        stockUsageService.deleteById(stockUsageCode);
        return ResponseEntity.noContent().build();
    }

    //전달여부 변경
    @PutMapping("/stock-usage")
    public ResponseEntity<Void> changeTransmission(
            @RequestParam final Long stockUsageCode
    ) {
        final Long usageCode = stockUsageService.changeTransmission(stockUsageCode);

        return ResponseEntity.created(URI.create("/api/v1/material/use/" + usageCode)).build();
    }

}
