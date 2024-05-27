package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialStockRepo extends JpaRepository<MaterialStock,Long> {

    @Query("SELECT ms FROM MaterialStock ms WHERE ms.actualQuantity > 0")
    List<MaterialStock> findMaterialStocksWithPositiveActualQuantity();

    List<MaterialStock> findAllByWarehouseCodeAndActualQuantityIsGreaterThan(long warehouseCode, int quntity);
}
