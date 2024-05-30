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

    @PostMapping("/storage/stock/{stockCode}")
    public ResponseEntity<Void> saveStorage(
            @PathVariable Long stockCode,
            @RequestBody StorageCreateRequest storageCreateRequest
            ){
        Long storageCode = storageService.saveStorage(stockCode,storageCreateRequest);
        return ResponseEntity.created(URI.create(null)).build();
    }
}
