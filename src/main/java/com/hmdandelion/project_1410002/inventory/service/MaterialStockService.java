package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.CombinedStockBySpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MaterialStockService {

    private final MaterialStockRepo materialStockRepo;

    public List<CombinedStockBySpecDTO> findStocksBySpec() {
        List<MaterialStock> stocks = materialStockRepo.findMaterialStocksWithPositiveActualQuantity();
        return stocks.stream()
                     .map(CombinedStockBySpecDTO::from)
                     .toList();
    }

    public List<MaterialStockSimpleDTO> findBywarehouseCode(long warehouseCode) {
        List<MaterialStock> stocks = materialStockRepo.findAllByWarehouseCodeAndActualQuantityIsGreaterThan(warehouseCode, 0);
        return stocks.stream()
                     .map(MaterialStockSimpleDTO::from)
                     .toList();
    }
}
