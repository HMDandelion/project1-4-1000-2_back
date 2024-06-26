package com.hmdandelion.project_1410002.inventory.domian.repository.material.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialStockRepoCustom {

    Page<MaterialStock> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode);

    MaterialStock getStockByCode(Long stockCode);

    List<Long> searchMaterialStockByMaterialName(String materialName);
}
