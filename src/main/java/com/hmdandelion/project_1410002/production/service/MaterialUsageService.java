package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NoContentsException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.service.MaterialStockService;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.repository.material.MaterialUsageRepo;
import com.hmdandelion.project_1410002.production.dto.material.MaterialUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.StockUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.response.MaterialUsageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialUsageService {

    private final MaterialUsageRepo materialUsageRepo;
    private final MaterialStockService materialStockService;
    private final StockUsageService stockUsageService;
    private final LineService lineService;


    public void usageCreate(WorkOrder workOrder) {
        MaterialUsage createOne = MaterialUsage.of(workOrder);
        materialUsageRepo.save(createOne);
    }


    public Page<MaterialUsageDTO> searchUse(Pageable pageable, String materialName, String sortType) {
        List<Long> stockCodes = new ArrayList<>();
        if (materialName != null) {
            stockCodes = materialStockService.searchMaterialStockByMaterialName(materialName);
        }

        Page<MaterialUsageDTO> list = materialUsageRepo.searchUse(pageable, stockCodes, sortType);
        if (list.isEmpty()) {
            throw new NoContentsException(ExceptionCode.NO_CONTENTS_MATERIAL_USE);
        }
        for (MaterialUsageDTO dto : list) {
            List<StockUsageDTO> stockUsages = stockUsageService.findByUsageCode(dto.getUsageCode());
            dto.addStockUsages(stockUsages);
            dto.addLineName(lineService.findNameByCode(dto.getLineCode()));
        }
        return list;
    }

    public MaterialUsageResponse findOne(Long usageCode) {

        return materialUsageRepo.getMaterialUsage(usageCode);
    }
}
