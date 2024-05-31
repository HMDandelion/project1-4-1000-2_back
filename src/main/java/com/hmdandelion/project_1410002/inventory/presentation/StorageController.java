package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageDestroyRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StorageStock;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StorageWarehouse;
import com.hmdandelion.project_1410002.inventory.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    /*재고 창고 배정(재고 수량보다 창고에 배정 된 갯수가 많으면 안됨.)*/
    @PostMapping("/storage/stock/{stockCode}")
    public ResponseEntity<Void> saveStorage(
            @PathVariable final Long stockCode,
            @RequestBody StorageCreateRequest storageCreateRequest
            ){
        Long storageCode = storageService.saveStorage(stockCode,storageCreateRequest);
        return ResponseEntity.created(URI.create(null)).build();
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
    public ResponseEntity<List<StorageStock>> getStorageStockByStockCode(
            @PathVariable final Long stockCode
    ){
        List<StorageStock> storageStocks = storageService.getStorageStockByStockCode(stockCode);

        return ResponseEntity.ok(storageStocks);
    }

    /*창고 별 이동 종류,상품명,수량,창고이름,등록 날짜*/
    @GetMapping("/storage/warehouse/{warehouseCode}")
    public ResponseEntity<List<StorageWarehouse>> getStorageWarehouseByWarehouseCode(
            @PathVariable final Long warehouseCode
    ){
        List<StorageWarehouse> storageWarehouses = storageService.getStorageWarehouseByWarehouseCode(warehouseCode);
        return ResponseEntity.ok(storageWarehouses);
    }

    /*파손 등록*/
    @PutMapping("/storage/destroy/{storageCode}")
    public ResponseEntity<Void> modifyDestroyQuantity(
            @PathVariable final Long storageCode,
            @RequestBody final StorageDestroyRequest destroyQuantity
    ){
        storageService.modifyDestroyQuantity(storageCode,destroyQuantity);
        return ResponseEntity.created(URI.create(null)).build();
    }

}
