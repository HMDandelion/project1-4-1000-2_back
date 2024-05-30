package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialSpecRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialStockRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialStockRepoCustom {

    List<MaterialStock> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode);

    MaterialStock getStockByCode(Long stockCode);
}
