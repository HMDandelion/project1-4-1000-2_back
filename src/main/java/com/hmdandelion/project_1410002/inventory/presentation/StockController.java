package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.product.response.AccumulateProduct;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.LeftStockDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProductDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.TodayStockDTO;
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
import java.util.List;

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
        return ResponseEntity.created(URI.create("/api/v1/stock"+stockCode)).build();
    }

    /*재고 조회(필터링)*/
    @GetMapping("/stock")
    public ResponseEntity<Page<StockProductDTO>> getStocks(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final Long productCode,
            @RequestParam(required = false) final StockType type,
            @RequestParam(required = false) final Long minQuantity,
            @RequestParam(required = false) final Long maxQuantity,
            @RequestParam(required = false) final AssignmentStatus assignmentStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "true") final Boolean sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<StockProductDTO> stocks = stockService.searchStocks(pageable,productCode,type,minQuantity,maxQuantity,assignmentStatus,startDate,endDate,sort);
        return ResponseEntity.ok(stocks);
    }

    /*재고 상세 조회(재고 코드로 조회)*/
    @GetMapping("/stock/{stockCode}")
    public ResponseEntity<StockProductDTO> getStock(
            @PathVariable final Long stockCode
    ){
        StockProductDTO stock = stockService.getStock(stockCode);
        return ResponseEntity.ok(stock);
    }

    /*재고 수정*/
    @PutMapping("/stock/{stockCode}")
    public ResponseEntity<Void> modifyStock(
            @PathVariable final Long stockCode,
            @RequestBody final StockUpdateRequest stockUpdateRequest
    ){
        stockService.modifyStock(stockCode,stockUpdateRequest);
        return ResponseEntity.created(URI.create("/api/v1/stock"+stockCode)).build();
    }

    /*재고 삭제(단,tbl_storage에 데이터가 있다면 삭제 불가능)*/
    @DeleteMapping("/stock/{stockCode}")
    public ResponseEntity<Void> deleteStockByStockCode(
            @PathVariable final Long stockCode
    ){
        stockService.deleteStockByStockCode(stockCode);
        return ResponseEntity.noContent().build();
    }

    /*누적 재고 조회*/
    @GetMapping("/stock/accumulate")
    public ResponseEntity<Integer> getAccumulateStock(){
        Integer sum = stockService.getAccumulateStock();
        return ResponseEntity.ok(sum);
    }

    /*상품 별 누적 재고 조회*/
    @GetMapping("/stock/product/accumulate")
    public ResponseEntity<List<AccumulateProduct>> getAccumulateStockByProductCode(

    ){
        List<AccumulateProduct> accumulateProducts = stockService.getAccumulateStockByProductCode();
        return ResponseEntity.ok(accumulateProducts);
    }

    /*오늘 등록 된 재고 갯수와 재고 수량 조회*/
    @GetMapping("/stock/today")
    public ResponseEntity<TodayStockDTO> getTodayStockInformation(){
        TodayStockDTO todayStockDTO = stockService.getTodayStockInformation();
        return ResponseEntity.ok(todayStockDTO);
    }

    /*해당 재고가 창고에 얼마나 배정 되었는지 확인*/
    @GetMapping("/stock/left/{stockCode}")
    public ResponseEntity<LeftStockDTO> getLeftStock(
            @PathVariable final Long stockCode
    ){
        LeftStockDTO leftStock = stockService.getLeftStock(stockCode);
        return ResponseEntity.ok(leftStock);
    }

}
