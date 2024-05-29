package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialStockRepoCustom {

    List<MaterialStockSimpleDTO> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode);

    MaterialStockDetailDTO getStockByCode(Long stockCode);
}
