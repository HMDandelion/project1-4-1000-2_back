package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.stock.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.CombinedStockBySpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialAnalyzeService {

    private final MaterialStockRepo materialStockRepo;

    public List<MaterialGraphModel> findStocksBySpec() {
        final List<MaterialStock> stocks = materialStockRepo.findMaterialStocksWithPositiveActualQuantity();
        final List<CombinedStockBySpecDTO> combined = stocks.stream()
                                                            .map(CombinedStockBySpecDTO::from)
                                                            .toList();

        return combined.stream()
                       .map(stock -> new MaterialGraphModel(stock.getMaterialName(),
                                                            stock.getActualQuantity(),
                                                            stock.getSafetyStock()))
                       .toList();
    }

    public List<MaterialStockSimpleDTO> findBywWrehouseCode(long warehouseCode) {
        List<MaterialStock> stocks = materialStockRepo.findAllByWarehouseWarehouseCodeAndActualQuantityIsGreaterThan(warehouseCode, 0);

        return stocks.stream()
                     .map(MaterialStockSimpleDTO::from)
                     .toList();
    }
}
