package com.hmdandelion.project_1410002.inventory.domian.repository.material.Stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialStockRepoCustom {

    List<MaterialStock> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode);

    MaterialStock getStockByCode(Long stockCode);
}
