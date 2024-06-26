package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageDestroyRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.*;
import com.hmdandelion.project_1410002.inventory.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    /*재고 저장 조회(필터링)*/
    @GetMapping("/storage/filter/{warehouseCode}")
    public ResponseEntity<Page<StorageFilterResponse>> getStocks(
            @PathVariable final Long warehouseCode,
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final Long productCode,
            @RequestParam(defaultValue = "1") final Long minQuantity,
            @RequestParam(defaultValue = "200") final Long maxQuantity,
            @RequestParam(defaultValue = "0") final Long startDate,
            @RequestParam(defaultValue = "100") final Long endDate,
            @RequestParam(defaultValue = "true") final Boolean quantitySort,
            @RequestParam(defaultValue = "false") final Boolean dateSort
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<StorageFilterResponse> storages = storageService.searchStorages(pageable, warehouseCode, productCode, minQuantity, maxQuantity, startDate, endDate, quantitySort, dateSort);
        return ResponseEntity.ok(storages);
    }

    /*재고 창고 배정(재고 수량보다 창고에 배정 된 갯수가 많으면 안됨.)*/
    @PostMapping("/storage/stock/{stockCode}")
    public ResponseEntity<Void> saveStorage(
            @PathVariable final Long stockCode,
            @RequestBody StorageCreateRequest storageCreateRequest
            ){
        Long storageCode = storageService.saveStorage(stockCode,storageCreateRequest);
        return ResponseEntity.created(URI.create("/api/v1/storage")).build();
    }

    /*재고 창고 배정 취소(저장 이력 삭제 조건: 삭제 되지 않은 재고,삭제 되지 않은 저장 이력,재고 이력 삭제 될 시 상태 값 변경)*/
    @DeleteMapping("/storage/{storageCode}")
    public ResponseEntity<Void> deleteStorage(
            @PathVariable final Long storageCode
    ){
        storageService.deleteStorage(storageCode);
        return ResponseEntity.noContent().build();
    }

    /*배정 된 재고의 창고와 수량과 재고의 초기 수량과 배정이 남은 갯수를 조회*/
    @GetMapping("/storage/stock/{stockCode}")
    public ResponseEntity<List<StorageStockDTO>> getStorageStockByStockCode(
            @PathVariable final Long stockCode
    ){
        List<StorageStockDTO> storageStocks = storageService.getStorageStockByStockCode(stockCode);

        return ResponseEntity.ok(storageStocks);
    }

    /*창고 별 이동 종류,상품명,수량,창고이름,등록 날짜*/
    @GetMapping("/storage/warehouse/{warehouseCode}")
    public ResponseEntity<PagingResponse> getStorageWarehouseByWarehouseCode(
            @PathVariable final Long warehouseCode,
            @RequestParam(defaultValue = "1") final Integer page
    ){
        Page<StorageWarehouseDTO> storageWarehouses = storageService.getStorageWarehouseByWarehouseCode(warehouseCode,page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(storageWarehouses);
        final PagingResponse pagingResponse = PagingResponse.of(storageWarehouses.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
    /*파손 등록*/
    @PutMapping("/storage/destroy/{storageCode}")
    public ResponseEntity<Void> modifyDestroyQuantity(
            @PathVariable final Long storageCode,
            @RequestBody final StorageDestroyRequest destroyQuantity
    ){
        storageService.modifyDestroyQuantity(storageCode,destroyQuantity);


        return ResponseEntity.created(URI.create("/api/v1/storage")).build();
    }

    /*총 파손률 계산*/
    @GetMapping("/storage/destroy")
    public ResponseEntity<Double> getDestroyRatio(){
        Double ratio = storageService.getDestroyRatio();
        return ResponseEntity.ok(ratio);
    }

    /*상품별 파손률 계산*/
    @GetMapping("/storage/product/destroy")
    public ResponseEntity<List<ProductDestroyDTO>> getProductDestroyRatio(){
        List<ProductDestroyDTO> productDestroys = storageService.getProductDestroyRatio();
        return ResponseEntity.ok(productDestroys);
    }

    /*창고 보관코드로 보관 이력 조회*/
    @GetMapping("/storage/{storageCode}")
    public ResponseEntity<StorageStockWarehouseDTO> getStorageByStorageCode(
            @PathVariable final Long storageCode
    ){
        StorageStockWarehouseDTO storageWarehouse = storageService.getStorageByStorageCode(storageCode);
        return ResponseEntity.ok(storageWarehouse);
    }

}
