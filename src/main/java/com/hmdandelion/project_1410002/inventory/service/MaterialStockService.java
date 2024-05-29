package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.CombinedStockBySpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialStockRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        final List<MaterialStock> stocks =materialStockRepo.searchMaterialStock(pageable, materialName, warehouseCode, specCategoryCode);
        if (stocks.isEmpty()) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_MATERIAL_NAME);
        }
        return stocks.stream().map(MaterialStockSimpleDTO::from).toList();
    }

    public MaterialStockDetailDTO findById(Long stockCode) {
        final MaterialStock stock = materialStockRepo.getStockByCode(stockCode);
        if (stock == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_STOCK_CODE);
        }
        return MaterialStockDetailDTO.from(stock);
    }

    @Transactional
    public Long save(SaveMaterialStockRequest request) {
        Warehouse warehouse =
        final Long stockCode = materialStockRepo.save(MaterialStock.from(request));
    }
}
