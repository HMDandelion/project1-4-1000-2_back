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

    /*재고 코드에 일치하는 보관 재고 초기 수량 합 조회*/

}
