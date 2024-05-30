package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StorageRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.warehouse.WarehouseRepo;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StorageStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StorageService {

    private final StorageRepo storageRepo;
    private final StockRepo stockRepo;
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;


    public Long saveStorage(Long stockCode, StorageCreateRequest storageCreateRequest) {

        System.out.println("stockCode = " + stockCode);
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        System.out.println("stock = " + stock);

        if(stock.getIsDelete()==true){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }

        List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stockCode,false);

        System.out.println("storages = " + storages);
        Long sum = 0L;
        
        for(Storage storage : storages){
            sum+=storage.getInitialQuantity();
        }
        sum+=storageCreateRequest.getInitialQuantity();
        System.out.println("sum = " + sum);

        Warehouse warehouse = warehouseRepo.findById(storageCreateRequest.getWarehouseCode()).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));

        if(stock.getQuantity()<sum){
            throw new CustomException(ExceptionCode.BAD_REQUEST_MORE_QUANTITY);
        }

        Storage newStorage = Storage.of(
                warehouse,
                storageCreateRequest.getInitialQuantity(),
                stock
        );

        storageRepo.save(newStorage);


        List<Storage> afterStorages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stockCode,false);

        if(afterStorages==null && afterStorages.isEmpty()){
            throw new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE);
        }

        System.out.println("afterStorages = " + afterStorages);
        Long afterSum = 0L;

        for(Storage storage : afterStorages){
            afterSum+=storage.getInitialQuantity();
        }
        AssignmentStatus change;
        if(afterSum==0){
            change=NOT_ASSIGNED;
        }else if(afterSum==stock.getQuantity()){
            change=FULLY_ASSIGNED;
        }else{
            change=PARTIALLY_ASSIGNED;
        }
        stock.modifyStatus(change);
        return newStorage.getStorageCode();
    }

    public void deleteStorage(Long storageCode) {
        Storage storage = storageRepo.findStorageByStorageCodeAndIsDelete(storageCode,false);
        if(storage==null){
            throw new CustomException(ExceptionCode.NOT_FOUND_STORAGE_CODE);
        }

        Long stockCode = storage.getStock().getStockCode();

        System.out.println("stockCode = " + stockCode);

        Stock stock =stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));

        storageRepo.deleteById(storageCode);

        List<Storage> afterStorages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stockCode,false);

        System.out.println("afterStorages = " + afterStorages);
        Long afterSum = 0L;

        for(Storage storageElement : afterStorages){
            afterSum+=storageElement.getInitialQuantity();
        }
        System.out.println("afterSum = " + afterSum);

        AssignmentStatus change;

        if(afterSum==0){
            change=NOT_ASSIGNED;
        }else if(afterSum==stock.getQuantity()){
            change=FULLY_ASSIGNED;
        }else{
            change=PARTIALLY_ASSIGNED;
        }
        stock.modifyStatus(change);
    }

    public List<StorageStock> getStorageStockByStockCode(Long stockCode) {
        List<StorageStock> resultList = new ArrayList<>();
        List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stockCode,false);
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        Product product = productRepo.findById(stock.getProduct().getProductCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        if(storages==null & storages.isEmpty()){
            throw new CustomException(ExceptionCode.NOT_FOUND_STORAGE_CODE);
        }
        Long sum = 0L;
        System.out.println("-------------------------------반복 문 시작 ------------------------");
        for(Storage storage:storages){
            sum+=storage.getInitialQuantity();
            System.out.println("sum = " + sum);

            Warehouse warehouse = warehouseRepo.findById(storage.getWarehouse().getWarehouseCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
            System.out.println("warehouse.getName() = " + warehouse.getName());

            System.out.println("product.getProductName() = " + product.getProductName());

            StorageStock storageStock = StorageStock.of(
                    warehouse.getName(),
                    storage.getInitialQuantity(),
                    product.getProductName(),
                    sum,
                    stock.getQuantity()
            );
            System.out.println("storageStock = " + storageStock);

            resultList.add(storageStock);
        }
        System.out.println("initialQuantity="+stock.getQuantity());
        System.out.println("resultList = " + resultList);
        return resultList;
    }
}
