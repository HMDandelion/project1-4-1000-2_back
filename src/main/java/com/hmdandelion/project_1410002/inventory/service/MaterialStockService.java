package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.CombinedStockBySpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialStockService {

    private static final Logger log = LoggerFactory.getLogger(MaterialStockService.class);
    private final MaterialStockRepo materialStockRepo;

    public List<CombinedStockBySpecDTO> findStocksBySpec() {
        List<MaterialStock> stocks = materialStockRepo.findMaterialStocksWithPositiveActualQuantity();

        return stocks.stream()
                     .map(CombinedStockBySpecDTO::from)
                     .toList();
    }

    public List<MaterialStockSimpleDTO> findBywarehouseCode(long warehouseCode) {
        List<MaterialStock> stocks = materialStockRepo.findAllByWarehouseWarehouseCodeAndActualQuantityIsGreaterThan(warehouseCode, 0);

        return stocks.stream()
                     .map(MaterialStockSimpleDTO::from)
                     .toList();
    }

    public List<MaterialStockSimpleDTO> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode) {
        return materialStockRepo.searchMaterialStock(pageable, materialName, warehouseCode, specCategoryCode);
    }
}
