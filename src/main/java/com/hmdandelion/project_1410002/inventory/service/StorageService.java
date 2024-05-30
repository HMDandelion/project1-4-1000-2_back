package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StorageRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.warehouse.WarehouseRepo;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StorageService {

    private final StorageRepo storageRepo;
    private final StockRepo stockRepo;
    private final WarehouseRepo warehouseRepo;

    public Long saveStorage(Long stockCode, StorageCreateRequest storageCreateRequest) {
        System.out.println("stockCode = " + stockCode);
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        System.out.println("stock = " + stock);
        Warehouse warehouse = warehouseRepo.findById(storageCreateRequest.getWarehouseCode()).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));

        if(storageCreateRequest.getInitialQuantity()>stock.getQuantity()){
            throw new CustomException(ExceptionCode.BAD_REQUEST_MORE_QUANTITY);
        }

        Storage newStorage = Storage.of(
                warehouse,
                storageCreateRequest.getInitialQuantity(),
                stock
        );
        return newStorage.getStorageCode();
    }
}
