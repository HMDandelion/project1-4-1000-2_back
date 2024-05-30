package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProduct;
import com.hmdandelion.project_1410002.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;


    /*재고 추가*/
    @PostMapping("/stock")
    public ResponseEntity<Void> saveStock(
            @RequestBody final StockCreateRequest stockRequest
    ){
        Long stockCode = stockService.saveStock(stockRequest);
        return ResponseEntity.created(URI.create(null)).build();
    }

    /*재고 조회(필터링)*/
    @GetMapping("/stock")
    public ResponseEntity<Page<StockProduct>> getStocks(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final Long productCode,
            @RequestParam(required = false) final StockType type,
            @RequestParam(required = false) final Long minQuantity,
            @RequestParam(required = false) final Long maxQuantity,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate endDate
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<StockProduct> stocks = stockService.searchStocks(pageable,productCode,type,minQuantity,maxQuantity,startDate,endDate);
        return ResponseEntity.ok(stocks);
    }

    /*재고 수정*/
    @PutMapping("/stock/{stockCode}")
    public ResponseEntity<Void> modifyStock(
            @PathVariable final Long stockCode,
            @RequestBody final StockUpdateRequest stockUpdateRequest
    ){
        stockService.modifyStock(stockCode,stockUpdateRequest);
        return ResponseEntity.created(URI.create(null)).build();
    }

    /*재고 삭제(단,tbl_storage에 데이터가 있다면 삭제 불가능)*/
    @DeleteMapping("/stock/{stockCode}")
    public ResponseEntity<Void> deleteStockByStockCode(
            @PathVariable final Long stockCode
    ){
        stockService.deleteStockByStockCode(stockCode);
        return ResponseEntity.noContent().build();
    }

    /**/

}
