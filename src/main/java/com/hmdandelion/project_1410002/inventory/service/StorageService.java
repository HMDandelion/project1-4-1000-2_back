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
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StorageDestroyRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        System.out.println("afterSum = " + afterSum);
        AssignmentStatus change;
        System.out.println("stock.getQuantity() = " + stock.getQuantity());
        if(afterSum==0){
            change=NOT_ASSIGNED;
        }else if(afterSum.equals(stock.getQuantity())){
            change=FULLY_ASSIGNED;
        }else{
            change=PARTIALLY_ASSIGNED;
        }
        System.out.println("change = " + change);
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
        }else if(afterSum.equals(stock.getQuantity())){
            change=FULLY_ASSIGNED;
        }else{
            change=PARTIALLY_ASSIGNED;
        }
        stock.modifyStatus(change);
    }

    public List<StorageStockDTO> getStorageStockByStockCode(Long stockCode) {
        List<StorageStockDTO> resultList = new ArrayList<>();
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

            StorageStockDTO storageStock = StorageStockDTO.of(
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

    public List<StorageWarehouseDTO> getStorageWarehouseByWarehouseCode(Long warehouseCode) {
        List<StorageWarehouseDTO> storageWarehouses = new ArrayList<>();
        List<Storage> storages = storageRepo.findStoragesByWarehouseWarehouseCodeAndIsDelete(warehouseCode,false);
        if(storages.isEmpty()&&storages==null){
            throw new CustomException(ExceptionCode.NOT_FOUND_STORAGE_CODE);
        }
        Warehouse warehouse = warehouseRepo.findById(warehouseCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
        for(Storage storage:storages){
            StorageWarehouseDTO storageWarehouse = StorageWarehouseDTO.of(
                    "입고",
                    storage.getStock().getProduct().getProductName(),
                    storage.getInitialQuantity(),
                    warehouse.getName(),
                    storage.getCreatedAt()
            );
            storageWarehouses.add(storageWarehouse);
            System.out.println("storageWarehouse = " + storageWarehouse);
        }
        return storageWarehouses;
    }


    public void modifyDestroyQuantity(Long storageCode, StorageDestroyRequest destroyQuantity) {
        Storage modifyStorage = storageRepo.findStorageByStorageCodeAndIsDelete(storageCode,false);
        List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(modifyStorage.getStock().getStockCode(),false);
        Stock stock = stockRepo.findById(modifyStorage.getStock().getStockCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        if(modifyStorage.getInitialQuantity()<destroyQuantity.getDestroyQuantity()){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DESTROY_QUANTITY);
        }
        modifyStorage.modifyDestroyQuantity(
                destroyQuantity.getDestroyQuantity()
        );


        Long standardSum = 0L;
        for(Storage entityStorage : storages){
            standardSum+=entityStorage.getActualQuantity();
            if(standardSum==0){
                entityStorage.modify();
            }
        }

        if(standardSum==0){
            stock.modifyIsDelete();
        }
    }

    public StorageStockWarehouseDTO getStorageByStorageCode(Long storageCode) {
        Storage storage = storageRepo.findStorageByStorageCodeAndIsDelete(storageCode,false);
        System.out.println("storage = " + storage);
        Stock stock = stockRepo.findById(storage.getStock().getStockCode()).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        System.out.println("2");
        Warehouse warehouse = warehouseRepo.findById(storage.getWarehouse().getWarehouseCode()).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
        System.out.println("3");
        StorageStockWarehouseDTO storageWarehouse = StorageStockWarehouseDTO.of(
                storage.getInitialQuantity(),
                storage.getDestroyQuantity(),
                stock.getStockCode(),
                storage.getActualQuantity(),
                storage.getCreatedAt(),
                stock.getQuantity(),
                stock.getCreatedAt(),
                stock.getType(),
                stock.getProduct().getProductCode(),
                stock.getAssignmentStatus(),
                warehouse.getWarehouseCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume()
        );
        return storageWarehouse;
    }

    public Page<StorageFilterResponse> searchStorages(Pageable pageable, Long productCode, Long minQuantity, Long maxQuantity,Long startDate,Long endDate,Boolean quantitySort, Boolean dateSort) {
        return storageRepo.searchStorages(pageable,productCode,minQuantity,maxQuantity,startDate,endDate,quantitySort,dateSort);
    }


    public Double getDestroyRatio() {
        List<Storage> storages = storageRepo.findAll();
        Long destroyTotal = 0L;
        Long total = 0L;
        for(Storage storage : storages){
            total+=storage.getInitialQuantity();
            destroyTotal+=storage.getDestroyQuantity();
        }

        System.out.println("total = " + total);
        System.out.println("destroyTotal = " + destroyTotal);

        Double ratio = (double)destroyTotal/total;
        BigDecimal ratioRounded = new BigDecimal(ratio).setScale(4, RoundingMode.HALF_UP);

        return ratioRounded.doubleValue();
    }

    public List<ProductDestroyDTO> getProductDestroyRatio() {
        List<ProductDestroyDTO> resultList = new ArrayList<>();

        List<Product> products = productRepo.findAll();

        for(Product product : products){
            List<Stock> stocks = stockRepo.findByProductProductCode(product.getProductCode());
            for(Stock stock : stocks) {
                List<Storage> storages = storageRepo.findStoragesByStockStockCode(stock.getStockCode());
                Long productSum = 0L;
                Long productDestroySum = 0L;
                for (Storage storage : storages) {
                    productSum += storage.getInitialQuantity();
                    productDestroySum += storage.getDestroyQuantity();
                }

                System.out.println("product.getProductName() = " + product.getProductName());
                System.out.println("productSum = " + productSum);
                System.out.println("productDestroySum = " + productDestroySum);

                Double ratio;
                if (productSum == 0) {
                    ratio = 0.0;
                } else {
                    ratio = (double)productDestroySum / productSum;
                }
                BigDecimal ratioRounded = new BigDecimal(ratio).setScale(10, RoundingMode.HALF_UP);
                ProductDestroyDTO productDestroy = ProductDestroyDTO.of(
                        product.getProductName(),
                        productSum,
                        ratioRounded.doubleValue()
                );
                resultList.add(productDestroy);
            }
        }
        return resultList;
    }
}
