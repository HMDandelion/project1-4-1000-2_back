package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;

import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.stock.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.warehouse.WarehouseRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockModifyRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialStockResponse;
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
    private final WarehouseRepo warehouseRepo;
    private final MaterialSpecRepo materialSpecRepo;
    private final WarehouseService warehouseService;


    public List<MaterialStockSimpleDTO> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode) {
        final List<MaterialStock> stocks = materialStockRepo.searchMaterialStock(pageable, materialName, warehouseCode, specCategoryCode);
        if (stocks.isEmpty()) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_MATERIAL_NAME);
        }
        return stocks.stream().map(MaterialStockSimpleDTO::from).toList();
    }

    public MaterialStockResponse findById(Long stockCode) {
        final MaterialStock stock = materialStockRepo.getStockByCode(stockCode);
        if (stock == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_STOCK_CODE);
        }
        return MaterialStockResponse.from(stock);
    }

    @Transactional
    public Long save(MaterialStockCreateRequest request) {
        Warehouse warehouse = warehouseRepo.findById(request.getWarehouseCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
        if (warehouse == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE);
        }
        MaterialSpec spec = materialSpecRepo.findBySpecCode(request.getSpecCode());
        if (spec == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_SPEC_CODE);
        }
        return materialStockRepo.save(MaterialStock.from(request, warehouse, spec)).getStockCode();
    }

    @Transactional
    public void delete(Long stockCode) {
        materialStockRepo.deleteById(stockCode);
    }

    @Transactional
    public Long modify(MaterialStockModifyRequest request) {

        Warehouse warehouse;
        MaterialStock stock = materialStockRepo.getStockByCode(request.getStockCode());
        if (stock == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_STOCK_CODE);
        }
        //만약 웨어하우스가 비어있지 않다면
        if (request.getWarehouseCode() != null) {
            //찾아서
            warehouse = warehouseRepo.findById(request.getWarehouseCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
            if (warehouse == null) {
                //잘못된 요청인가 확인하고
                throw new NotFoundException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE);
            }
        } else {
            // 비어있으면 기존의 것을 유지
            warehouse = stock.getWarehouse();
        }
        stock.modifyFrom(request, warehouse);
        return stock.getStockCode();
    }

    public List<Long> searchMaterialStockByMaterialName(String materialName) {

        return materialStockRepo.searchMaterialStockByMaterialName(materialName);
    }
}
