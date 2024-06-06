package com.hmdandelion.project_1410002.inventory.domian.repository.material.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialStockRepo extends JpaRepository<MaterialStock, Long>, MaterialStockRepoCustom {

    @Query("SELECT ms FROM MaterialStock ms WHERE ms.actualQuantity > 0")
    List<MaterialStock> findMaterialStocksWithPositiveActualQuantity();

    List<MaterialStock> findAllByWarehouseWarehouseCodeAndActualQuantityIsGreaterThan(long warehouseCode, int quantity);
}
