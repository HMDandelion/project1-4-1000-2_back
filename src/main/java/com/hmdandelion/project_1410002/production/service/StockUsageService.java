package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockModifyRequest;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import com.hmdandelion.project_1410002.inventory.service.MaterialStockService;
import com.hmdandelion.project_1410002.production.domain.entity.material.StockUsage;
import com.hmdandelion.project_1410002.production.domain.repository.material.StockUsageRepo;
import com.hmdandelion.project_1410002.production.dto.material.StockUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.request.StockUsageCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockUsageService {

    private final StockUsageRepo stockUsageRepo;
    private final MaterialStockService materialStockService;

    public List<StockUsageDTO> findByUsageCode(Long usageCode) {
        List<StockUsage> list = stockUsageRepo.findAllByUsageCode(usageCode);
        List<StockUsageDTO> result = new ArrayList<>();
        for (StockUsage item : list) {
            String MaterialName = materialStockService.findById(item.getStockCode()).getSpec().getMaterialName();
            result.add(StockUsageDTO.from(item, MaterialName));
        }
        return result;
    }

    @Transactional
    public void createStockUsage(StockUsageCreateRequest request) {
        StockUsage newStockUsage = StockUsage.from(request);
        stockUsageRepo.save(newStockUsage);
        MaterialStockModifyRequest stockRequest = new MaterialStockModifyRequest(
                request.getStockCode(),
                null,
                -request.getUsedQuantity().intValue(),
                LocalDateTime.now(),
                "자재 사용으로 수정됨.(System)"
        );
        materialStockService.modify(stockRequest);
    }
}
