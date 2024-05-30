package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageCreateRequest;
import com.hmdandelion.project_1410002.inventory.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    /*재고 창고 배정(재고 수량보다 창고에 배정 된 갯수가 많으면 안됨.)*/
    @PostMapping("/storage/stock/{stockCode}")
    public ResponseEntity<Void> saveStorage(
            @PathVariable Long stockCode,
            @RequestBody StorageCreateRequest storageCreateRequest
            ){
        Long storageCode = storageService.saveStorage(stockCode,storageCreateRequest);
        return ResponseEntity.created(URI.create(null)).build();
    }

    /*재고 창고 배정 취소(저장 이력 삭제 조건: 삭제 되지 않은 재고,삭제 되지 않은 저장 이력,재고 이력 삭제 될 시 상태 값 변경)*/
    @DeleteMapping("/storage/{storageCode}")
    public ResponseEntity<Void> deleteStorage(
            @PathVariable Long storageCode
    ){
        storageService.deleteStorage(storageCode);
        return ResponseEntity.noContent().build();
    }

}
