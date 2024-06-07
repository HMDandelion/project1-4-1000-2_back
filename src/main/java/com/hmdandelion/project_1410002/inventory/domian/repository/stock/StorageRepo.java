package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepo extends JpaRepository<Storage,Long>, StorageRepoCustom  {
    List<Storage> findStoragesByStockStockCodeAndIsDelete(Long stockCode,Boolean isDelete);

    List<Storage> findStoragesByStockStockCode(Long stockCode);

    Storage findStorageByStorageCodeAndIsDelete(Long storageCode,Boolean isDelete);

    List<Storage> findStoragesByWarehouseWarehouseCodeAndIsDelete(Long warehouseCode,Boolean isDelete);

}
